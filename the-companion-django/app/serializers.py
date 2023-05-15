from app.models import User, SensorData
from rest_framework import serializers
#from rest_framework.renderers import JSONRenderer

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'pw', 'email']


class SensorDataSerializer(serializers.ModelSerializer):
    class Meta:
        model = SensorData
        fields = ['time', 'latitude', 'longitude', 'acceleration_x',
                   'acceleration_y','acceleration_z','onwifi']
