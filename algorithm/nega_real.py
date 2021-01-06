import pandas as pd
import numpy as np
import math as mt
from sparsesvd import sparsesvd #행렬분해를 위해 사용됩니다.
import numpy as np
from scipy.sparse import csc_matrix #희소행렬을 위해 사용됩니다.
from scipy.sparse.linalg import * #행렬 곱셈을 위해 사용됩니다.
import random

data = pd.read_excel("/Users/jongeun/PycharmProjects/haneum/final_data.xlsx", error_bad_lines=False, header=None)
df = pd.DataFrame(data)


menu = []
menu.append(df[:][0])
m = np.array(menu)
m.shape = (4106)
new_menu = []       # 중복 제거한 메뉴 리스트 생성
for v in m:
    if v not in new_menu:
        new_menu.append(v)


user = []
user.append(df[:][1])
u = np.array(user)
u.shape = (4106)    # print(u.shape)의 결과 [1,4106]

new_user = []
for v in u:
    if v not in new_user:
        new_user.append(v)

user_num = len(new_user)


tag = []
tag.append(df[:][2])
t = np.array(tag)
t.shape = (4106)    # print(t.shape)의 결과 [1,4106]

new_tag = []
for v in t:
    if v not in new_tag:
        new_tag.append(v)

tag_num = len(new_tag)


# 사용자 평점 행렬(URM)의 차원을 정의할 상수
MAX_PID = tag     # tag 수
MAX_UID = user_num     # User 수


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
# 테스트 사용자를 위한 예상 평점을 계산
# https://leebaro.tistory.com/entry/SVD%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%B6%94%EC%B2%9C-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0
def computeEstimatedRatings(urm, U, S, Vt, uTest, K, test):
    rightTerm = S * Vt

    estimatedRatings = np.zeros(shape=(MAX_UID, MAX_PID), dtype=np.float16)
    for userTest in uTest:
        prod = U[userTest, :] * rightTerm
        estimatedRatings[userTest, :] = prod.todense()
        recom = (-estimatedRatings[userTest, :]).argsort()[:250]
    return recom

K=2

#샘플 사용자 평점 행렬을 초기화합니다.
#urm = np.array(df_ratings)

data = []


for y in range(len(new_user)):
    ydata = []
    ry = random.randint(1,5)
    for r in range(len(new_tag)):
        ydata.append((random.randint(1,5)))
    data.append(ydata)


urm = pd.DataFrame(data=data,columns=new_tag,index=new_user)
urm = np.array(urm)
urm = csc_matrix(urm, dtype=np.float32)


#입력된 사용자 평점 행렬의 SVD 를 계산합니다.
U, S, Vt = computeSVD(urm, K)
#print(U,S,Vt)


#테스트 사용자의 사용자 ID 를 4로, 평점을 [0, 0, 5, 0]으로 설정합니다.
uTest = [3]
print("User id for whom recommendations are needed: %d" % uTest[0])

#테스트 사용자에 대한 예상 평점
print("Predictied ratings:")
uTest_recommended_items = computeEstimatedRatings(urm, U, S, Vt, uTest, K, True)
print(uTest_recommended_items)

