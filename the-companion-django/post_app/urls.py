from django.urls import path
from post_app import views

urlpatterns = [
    path('recommend_list/auto/', views.SensorView.as_view()),
    path('recommend_list/auto_temp/', views.SensorView.as_view())
]