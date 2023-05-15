from django.urls import path
from app import views

urlpatterns = [
    path('user/', views.user_list),
    path('user/login/', views.LoginView.as_view()),
    path('user/<str:pk>/', views.user_detail),
    path('sensordata/', views.SensorView.as_view()),
]