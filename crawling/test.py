import pandas as pd
import requests
import json

dong = pd.read_csv(
    "C:/Users/multicampus/Desktop/특화데이터/전국동이름.csv", encoding='euc-kr')

f = open('C:/Users/multicampus/Desktop/output.txt', 'w')
addr_name = []
plc_name = []
corX = []
corY = []
cnt = 1
for b in dong.index:
    address = dong["name"][b]
    if not address:
        break
    url = "https://dapi.kakao.com/v2/local/search/address.json?query="+address
    headers = {'Authorization': 'KakaoAK 890a8bd3341e097d41c34c82eb9ed922'}
    result = json.loads(str(requests.get(url, headers=headers).text))
    x = result['documents'][0]['address']['x']  # 경도
    y = result['documents'][0]['address']['y']  # 위도

    convs = {}

    # 편의점 위치
    for i in range(1, 46):
        headers = {'Authorization': 'KakaoAK 890a8bd3341e097d41c34c82eb9ed922'}
        # 파라미터 가져오기
        params = {
            'x': float(x),
            'y': float(y),
            'radius': 10000,  # 범위 0 ~20000까지 단위는 미터
            'page': i,
            'size': 15,
            'sort': 'distance'
        }

        # 카테고리 키워드
        keywords = '카페'

        # API 접근
        url = 'https://dapi.kakao.com/v2/local/search/keyword.json?query={}'.format(
            keywords)
        places = requests.get(url, headers=headers, params=params).json()[
            'documents']
        # print(places)

        if i > 1:
            if convs[i-1][0] == places[0]:
                break
        # 빈 배열이면 stop
        if places == []:
            break
        else:
            convs[i] = places
        # convs = places

        if len(places) < 15:
            break

    address_name = []
    place_name = []
    cox = []
    coy = []

    # 편의점 경위도 cox, coy에 순차적으로 저장해서 아래의 corX , corY에 넣기 위한 작업
    for i in range(1, len(convs)+1):
        for a in range(len(convs[i])):

            add = convs[i][a]['address_name']
            plc = convs[i][a]['place_name']
            address_name.append(add)
            place_name.append(plc)
            cox.append(convs[i][a]['x'])
            coy.append(convs[i][a]['y'])

    
    addr_name += list((address_name))
    plc_name += list((place_name))
    corX += list((cox))
    corY += list((coy))
    
# 빈 데이터 프레임 생성해서 column에 저장
place = pd.DataFrame()
place['address'] = addr_name
place['place'] = plc_name
place['X'] = corX
place['Y'] = corY

# csv 파일로 내보내기
place.to_csv('C:/Users/multicampus/Desktop/전국카페데이터.csv', encoding='CP949')
