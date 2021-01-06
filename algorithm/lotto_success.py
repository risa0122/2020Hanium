import math as mt
import csv
import random

from sparsesvd import sparsesvd #행렬분해를 위해 사용됩니다.
import numpy as np
from scipy.sparse import csc_matrix #희소행렬을 위해 사용됩니다.
from scipy.sparse.linalg import * #행렬 곱셈을 위해 사용됩니다.
import operator
# 사용자 평점 행렬(URM)의 차원을 정의할 상수
MAX_PID = 45
MAX_UID = 45


# 사용자 평점 행렬의 SVD 를 계산합니다.
def computeSVD(urm, K):
    U, s, Vt = sparsesvd(urm, K)

    dim = (len(s), len(s))
    S = np.zeros(dim, dtype=np.float32)
    for i in range(0, len(s)):
        S[i, i] = mt.sqrt(s[i])

    U = csc_matrix(np.transpose(U), dtype=np.float32)
    S = csc_matrix(S, dtype=np.float32)
    Vt = csc_matrix(Vt, dtype=np.float32)

    return U, S, Vt


# 테스트 사용자를 위한 예상 평점을 계산합니다.
def computeEstimatedRatings(urm, U, S, Vt, uTest, K, test):
    rightTerm = S * Vt

    estimatedRatings = np.zeros(shape=(MAX_UID, MAX_PID), dtype=np.float16)
    for userTest in uTest:
        prod = U[userTest, :] * rightTerm
        # 벡터를 dense format 으로 변환하여
        # 최고 예상 평점을 가진 영화를 찾아냅니다.
        estimatedRatings[userTest, :] = prod.todense()
        recom = (-estimatedRatings[userTest, :]).argsort()[:250]
    return recom


#SVD 계산에 사용될 것입니다 (잠재 인수의 개수).
K=2

#샘플 사용자 평점 행렬을 초기화합니다.
finallist = []


#샘플 사용자 데이터 넣는 과정
#숫자가 클수록 좋아하는 사용자
#모든 숫자를 2만큼 좋아하는 사용자
#2~45의 배수를 좋아하는 사용자
#총 46명
#좋아하는 숫자는 10점 나머지는 5점
for i in range(0, 45):
    llist = []
    for j in range(1, 46):
        if i == 0:
            llist.append(j)
        if i == 1:
            llist.append(2)
        if i >= 2:
            if (j % i == 0 and i >= 2):
                llist.append(10)
            else:
                llist.append(5)

    finallist.append(llist)

#최근 10회 로또 당첨번호
latest = [2, 15, 16, 21, 22, 28, 7, 19, 23, 24, 36, 39, 5, 18, 20, 23, 30, 34, 7, 13, 16, 18, 35, 38, 8, 19, 20, 21, 33, 39, 18, 21, 28, 35, 37, 42, 6, 7, 12, 22, 26, 36, 5, 12, 25, 26, 38, 45, 16, 26, 31, 38, 39, 41, 19, 32, 37, 40, 41, 43]


#최근 10회 로또 당첨번호의 빈도수 딕셔너리 생성 {번호 : 빈도수}
latest_dic = {}

for i in latest:
    if i in latest_dic.keys():
        latest_dic[i] += 1
    else:
        latest_dic[i] = 1

# res = sorted(latest_dic.items(), key=operator.itemgetter(1), reverse=False)


#선호번호와 비선호번호 저장
unprefer = [5, 10, 15, 20, 25, 30, 35, 40, 45, 13, 31, 32]
prefer = [9, 18, 27, 36, 45]


#user의 평균 평점 번호 초기화
user = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]


#user의 선호번호 10점 부여
for i in prefer:
    user[i-1] = 10

#비선호 번호 1점 부여
for i in unprefer:
    user[i-1] = 1

#user 리스트 제대로 넣어졌는지 확인
print(user)

#user평점 리스트를 사용자 전체 리스트에 추가
finallist.append(user)

urm = np.array(finallist)
urm = csc_matrix(urm, dtype=np.float32)

#입력된 사용자 평점 행렬의 SVD 를 계산
U, S, Vt = computeSVD(urm, K)

uTest = [44]
print("User id for whom recommendations are needed: %d" % uTest[0])


#테스트 사용자에 대한 예상 평점
print("Predictied ratings:")
uTest_recommended_items = computeEstimatedRatings(urm, U, S, Vt, uTest, K, True)


#최근 10회 빈도수로 가산점 부여
for key in latest_dic.keys():
    uTest_recommended_items[key-1] = latest_dic[key]


#사용자 비선호 번호는 확실하게 1로 다시 점수 부여 (추천이 아예 안되게끔 막아버림)
for i in unprefer:
    uTest_recommended_items[i - 1] = 1

#최종 딕셔너리 생성 {로또 번호 : 최종점수}
item = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45]
final_dic = {name:value for name, value in zip(item, uTest_recommended_items)}


#2점 미만 점수 거르기
dele = []
for key, value in final_dic.items():
    if value < 2:
        dele.append(key)

for i in dele:
    del(final_dic[i])
rec = random.sample(final_dic.keys(), 6)


#결과 출력
print(uTest_recommended_items)
print(final_dic)
print("\nrecommended lotto number: ")
print(rec)