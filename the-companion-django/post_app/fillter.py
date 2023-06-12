import math
import sqlite3

# 상수 설정
LOCATION_FILTER_THRESHOLD = 10.0  # 위치 보정을 위한 오차 임계값 (미터)
VECTOR_FILTER_THRESHOLD = 91.67  # 속도 필터링을 위한 임계값 (m/s) KTX속도
ACCELERATION_FILTER_THRESHOLD = 2.0  # 가속도 필터링을 위한 임계값 (m/s^2)

def location_filter(latitude, longitude, wifi_on):
    if wifi_on:
        # Wi-Fi 연결로 위치 정보 보정
        corrected_latitude = ...  # Wi-Fi를 이용한 위치 보정 로직 구현
        corrected_longitude = ...  # Wi-Fi를 이용한 위치 보정 로직 구현
        return corrected_latitude, corrected_longitude
    else:
        return latitude, longitude

def vector_filter(rows):
    filtered_rows = []

    for i in range(len(rows)):
        if i == 0:
            filtered_rows.append(rows[i])
        else:
            lat1, lon1, time1, acc_x1, acc_y1, acc_z1 = rows[i-1]
            lat2, lon2, time2, acc_x2, acc_y2, acc_z2 = rows[i]

            # GPS 좌표 간 거리 계산
            distance = math.sqrt((lat2 - lat1)**2 + (lon2 - lon1)**2)

            # 시간 차 계산
            time_diff = time2 - time1

            # 속도 계산
            speed = distance / time_diff

            if speed <= VECTOR_FILTER_THRESHOLD:
                filtered_rows.append(rows[i])

    return filtered_rows

def absolute_filter(rows):
    filtered_rows = []

    for i in range(len(rows)):
        if i == 0 or i == len(rows) - 1:
            filtered_rows.append(rows[i])
        else:
            lat, lon, time, acc_x, acc_y, acc_z = rows[i]

            # 가속도 벡터 크기 계산
            acceleration = math.sqrt(acc_x**2 + acc_y**2 + acc_z**2)

            if acceleration <= ACCELERATION_FILTER_THRESHOLD:
                filtered_rows.append(rows[i])
            else:
                lat_prev, lon_prev, time_prev, _, _, _ = rows[i-1]
                lat_next, lon_next, time_next, _, _, _ = rows[i+1]

                # 이전 및 다음 GPS 좌표의 산술평균으로 값을 보정
                corrected_lat = (lat_prev + lat_next) / 2
                corrected_lon = (lon_prev + lon_next) / 2

                filtered_rows.append((corrected_lat, corrected_lon, time, acc_x, acc_y, acc_z))

    return filtered_rows

def preprocess_raw_data(db_id, db_pw, db_lat, db_lon, db_wifi_on, db_acc_x, db_acc_y, db_acc_z):
    # 데이터베이스 연결
    conn = sqlite3.connect("your_database.db")  # 여기서 "your_database.db"는 실제 데이터베이스 파일의 경로입니다.
    cursor = conn.cursor()

    # Raw 데이터 가져오기
    cursor.execute("SELECT * FROM your_table")  # 여기서 "your_table"은 실제 데이터가 저장된 테이블의 이름입니다.
    rows = cursor.fetchall()

    # 위치 보정 (Location Filter)
    filtered_locations = []
    for row in rows:
        latitude, longitude, wifi_on = row[db_lat], row[db_lon], row[db_wifi_on]
        corrected_latitude, corrected_longitude = location_filter(latitude, longitude, wifi_on)
        filtered_locations.append((corrected_latitude, corrected_longitude))

    # 속도 필터링 (Vector Filter)
    filtered_vectors = vector_filter(filtered_locations)

    # 가속도 필터링 (Absolute Filter)
    filtered_data = absolute_filter(filtered_vectors)

    # 보정된 데이터를 데이터베이스에 저장 또는 반환

    # 데이터베이스 연결 종료
    conn.close()

    return filtered_data
