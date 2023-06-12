from django.shortcuts import render
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

class SensorView(APIView):
    def post(self, request):
        #아이디 및 비밀번호 확인
         return  Response({"num": "1", "id1" : "1", "made_by1" : "DemoUser"},status=200)
        #return  Response({"num": "3", "id1" : "1", "made_by1" : "Test_user025", "id1" : "2", "made_by2" : "test_user004", "id3" : "3", "made_by3" : "bhwkd"},status=200)
