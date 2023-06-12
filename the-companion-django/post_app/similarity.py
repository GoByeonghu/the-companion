from math import sqrt

def calculate_distance(vertex1, vertex2):
    """
    두 정점 간의 거리를 계산하는 함수
    """

    lat1, lon1 = vertex1["latitude"], vertex1["longitude"]
    lat2, lon2 = vertex2["latitude"], vertex2["longitude"]

    # 유클리드 거리 계산
    distance = sqrt((lat2 - lat1)**2 + (lon2 - lon1)**2)

    return distance

def calculate_similarity(user1_pattern, user2_pattern):
    """
    두 사용자의 대표 이동경로 패턴을 비교하여 유사도를 계산하는 함수
    """

    # 출발지와 도착지 거리의 유사도 계산
    start_distance = calculate_distance(user1_pattern["start_vertex"], user2_pattern["start_vertex"])
    end_distance = calculate_distance(user1_pattern["end_vertex"], user2_pattern["end_vertex"])
    distance_similarity = 1 / (1 + abs(start_distance - end_distance))

    # 이동시간의 유사도 계산
    time_similarity = 1 / (1 + abs(user1_pattern["end_vertex"]["timestamp"] - user1_pattern["start_vertex"]["timestamp"] - user2_pattern["end_vertex"]["timestamp"] - user2_pattern["start_vertex"]["timestamp"]))

    # 이동수단의 유사도 계산
    transportation_similarity = 1 if user1_pattern["start_vertex"]["transportation"] == user2_pattern["start_vertex"]["transportation"] else 0

    # 유사도 점수 계산
    similarity_score = distance_similarity + time_similarity + transportation_similarity

    return similarity_score

def find_most_similar_user(user_patterns, current_user):
    """
    주어진 사용자 패턴들 중에서 가장 유사한 사용자를 찾는 함수
    """

    max_similarity = 0
    most_similar_user = None

    for user, pattern in user_patterns.items():
        if user == current_user:
            continue

        similarity = calculate_similarity(pattern, user_patterns[current_user])

        if similarity > max_similarity:
            max_similarity = similarity
            most_similar_user = user

    return most_similar_user, max_similarity
