import math
import sqlite3

# 상태 정의
MOVING = "move"
STOPPED = "stop"

# 이동수단 정의
WALKING = "walking"
CAR = "car"
SUBWAY = "subway"

# 상수 설정
STOP_DECISION = 10.0  # 정지 판단 기준 속도 (m/s)
ACCELERATION_THRESHOLD = 0.2  # 가속벡터 기준 값 (m/s^2)
WALKING_SPEED_THRESHOLD = 1.4  # 도보 판단 기준 속도 (m/s)
CAR_SPEED_THRESHOLD = 10.0  # 자동차 판단 기준 속도 (m/s)
SUBWAY_SPEED_THRESHOLD = 20.0  # 지하철 판단 기준 속도 (m/s)

def create_movement_graph(db_id, db_pw, db_lat, db_lon, db_wifi_on):
    # 데이터베이스 연결
    conn = sqlite3.connect("your_database.db")  # 여기서 "your_database.db"는 실제 데이터베이스 파일의 경로입니다.
    cursor = conn.cursor()

    # Raw 데이터 가져오기
    cursor.execute("SELECT * FROM your_table")  # 여기서 "your_table"은 실제 데이터가 저장된 테이블의 이름입니다.
    rows = cursor.fetchall()

    # 이동경로 그래프 생성
    movement_graph = {}

    # 데이터 처리
    previous_vertex = None
    for row in rows:
        user_id, password, latitude, longitude, wifi_on, timestamp = row

    # row_time 정의
        row_time = timestamp

        # 속도 계산
        if previous_vertex is None:
            speed = 0.0
        else:
            distance = math.sqrt((latitude - previous_vertex["latitude"])**2 + (longitude - previous_vertex["longitude"])**2)
            time = row_time - previous_vertex["timestamp"]
            speed = distance / time

        # 상태 판단
        if speed <= STOP_DECISION or wifi_on:
            state = STOPPED
        else:
            state = MOVING

        # Vertex 생성
        timestamp = row_time
        vertex = {
            "timestamp": timestamp,
            "latitude": latitude,
            "longitude": longitude,
            "state": state,
            "speed": speed
        }

        # Vertex를 그래프에 추가
        if state == STOPPED:
            if previous_vertex is not None:
                if previous_vertex["state"] == STOPPED:
                    previous_vertex["timestamp"] = min(previous_vertex["timestamp"], timestamp)
                    previous_vertex["latitude"] = (previous_vertex["latitude"] + latitude) / 2
                    previous_vertex["longitude"] = (previous_vertex["longitude"] + longitude) / 2
                else:
                    movement_graph[previous_vertex["timestamp"]] = previous_vertex
                    previous_vertex = vertex
            else:
                previous_vertex = vertex
        else:
            if previous_vertex is not None:
                movement_graph[previous_vertex["timestamp"]] = previous_vertex
                previous_vertex = None

        # 가속벡터 계산
        if previous_vertex is not None:
            acceleration = (speed - previous_vertex["speed"]) / time

            # 이동수단 결정
            if speed <= WALKING_SPEED_THRESHOLD:
                transportation = WALKING
            elif speed <= CAR_SPEED_THRESHOLD:
                transportation = CAR
            elif speed <= SUBWAY_SPEED_THRESHOLD:
                transportation = SUBWAY
            else:
                transportation = None

            # Edge 추가
            if transportation is not None and acceleration <= ACCELERATION_THRESHOLD:
                edge = {
                    "from_timestamp": previous_vertex["timestamp"],
                    "to_timestamp": timestamp,
                    "transportation": transportation
                }
                movement_graph[previous_vertex["timestamp"]]["edges"].append(edge)

    # 마지막 Vertex 추가
    if previous_vertex is not None:
        movement_graph[previous_vertex["timestamp"]] = previous_vertex

    # 데이터베이스 연결 종료
    conn.close()

    return movement_graph