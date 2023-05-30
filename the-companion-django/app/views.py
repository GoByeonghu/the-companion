from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from rest_framework.parsers import JSONParser
from rest_framework.views import APIView
from rest_framework.response import Response
from app.models import User, SensorData
from app.serializers import UserSerializer, SensorDataSerializer

import jwt, datetime
from local_settings import ALGORITHM, LOCAL_SECRET_KEY


def Authenticate(token):
    #print(datetime.datetime.now())
    #token = request.COOKIES.get('token')
    #token = request.data['token']
    print(f'토큰: {token}')
    if not token :
        print("없음")
        return False
    try:
        #payload = jwt.decode(token.encode('utf-8'), LOCAL_SECRET_KEY, algorithms=[ALGORITHM])
        payload = jwt.decode(token, LOCAL_SECRET_KEY, algorithms=[ALGORITHM])
    except jwt.ExpiredSignatureError:
        print("변조됨")
        return False 

    user = User.objects.get(id = payload['id'])
    return user.id

@csrf_exempt
def user_list(request):
    """
    List all code snippets, or create a new snippet.
    """
    if request.method == 'GET':
        users = User.objects.all()
        serializer = UserSerializer(users, many=True)
        return JsonResponse(serializer.data, safe=False)

    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = UserSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return JsonResponse(serializer.data, status=200)
        return JsonResponse(serializer.errors, status=400)

@csrf_exempt
def user_detail(request, pk):
    """
    Retrieve, update or delete a code snippet.
    """
    try:
        users = User.objects.get(pk=pk)
    except User.DoesNotExist:
        return HttpResponse(status=404)

    if request.method == 'GET':
        serializer = UserSerializer(users)
        return JsonResponse(serializer.data)

    elif request.method == 'PUT':
        data = JSONParser().parse(request)
        serializer = UserSerializer(users, data=data)
        if serializer.is_valid():
            serializer.save()
            return JsonResponse(serializer.data)
        return JsonResponse(serializer.errors, status=400)

    elif request.method == 'DELETE':
        users.delete()
        return HttpResponse(status=204)
    

class LoginView(APIView):
    def post(self, request):
        #아이디 및 비밀번호 확인
        print(f'DDDDDDDDDDD::::::::::::{request.data}')
        try:
            user = User.objects.get(id=request.data['id'])
            if(user.pw != request.data['pw']):
                return Response({ "error": "wrong pw"},status=409)
        except User.DoesNotExist:
            return Response({ "error": "does not exist id"},status=409)
        except User.MultipleObjectsReturned:
            return Response({ "fatal error": "interserver error.(duplicated id)"},status=500)
        #jwt생성
        payload = {
            'id' : user.id,
            'exp' : datetime.datetime.now() + datetime.timedelta(weeks=30),
            'iat' : datetime.datetime.now() - datetime.timedelta(hours = 10)
        }

        print("iat::"+payload['iat'].strftime("%m/%d/%Y, %H:%M:%S"))

        token = jwt.encode(payload,LOCAL_SECRET_KEY,algorithm=ALGORITHM)
        res=Response()
        res.data = {
            'token' : token
        }

        return res

    
class SensorView(APIView):
    def post(self, request):
        #아이디 및 비밀번호 확인
        if(Authenticate(request.data['token'])):
            print(Authenticate(request.data['token']))
        else:
            return  Response({"error: Unauthorized"}, status=401)
        target_user = User.objects.get(id=request.data['user_id'])
        SensorData.objects.create(
            user = target_user,
            time=request.data['time'], 
            latitude=request.data['latitude'], 
            longitude=request.data['longitude'], 
            acceleration_x=request.data['acceleration_x'],
            acceleration_y=request.data['acceleration_y'],
            acceleration_z=request.data['acceleration_z'],
            onwifi=request.data['onwifi']
        )

        return  Response({"message: post sensor data successfully"},status=200)