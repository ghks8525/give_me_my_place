import random

id=[1,2,3,4,5]
dow=[]
hour=[]
StartTime=[]
EndTime=[]

f=open("user_pattern.csv","w")
f.write("user_ID,dow,StartTime,EndTime\n")
# f.write("user_ID,dow,hour,StartTime,EndTime\n")

# user1
# 주중엔 저녁시간에 와서 23~24시경 퇴실
# 주말엔 안옴
# for i in range(1000):
#     dow.append(random.randint(1,6))
#     StartTime.append(random.randint(1120,1160))
#     EndTime.append(random.randint(1380,1440))
#     hour.append(EndTime[i]-StartTime[i])

# for i in range(1000):
#     f.write(str(id[0])+','+str(dow[i])+','+str(StartTime[i])+','+str(EndTime[i])+'\n')
#     # f.write(str(id[0])+','+str(dow[i])+','+str(hour[i])+','+str(StartTime[i])+','+str(EndTime[i])+'\n')
# dow=[]
# hour=[]
# StartTime=[]
# EndTime=[]


# user2
# 주중 평일 상관 없이 하루 평균 8시간정도 공부하고 퇴실
# for i in range(1000):
#     dow.append(random.randint(1,7))
#     StartTime.append(random.randint(420,960)) # 입장시각 범위 7~16시
#     hour.append(random.randint(460,500)) # 사용시간 범위 7:40~8:20
#     EndTime.append(StartTime[i]+hour[i]) 
# for i in range(1000):
#     f.write(str(id[1])+','+str(dow[i])+','+str(StartTime[i])+','+str(EndTime[i])+'\n')
#     # f.write(str(id[1])+','+str(dow[i])+','+str(hour[i])+','+str(StartTime[i])+','+str(EndTime[i])+'\n')

# dow=[]
# hour=[]
# StartTime=[]
# EndTime=[]


# # user3
# # 주말에 하루종일 공부. 9시쯤 와서 17시쯤 퇴실
# # 주중에 저녁시간(18시경)에 와서 22시경 퇴실
# for i in range(1000):
#     dow.append(random.randint(1,7))
#     if dow[i]<=5: #평일일 경우
#         StartTime.append(random.randint(1070,1090)) # 입실시각 약 18시
#         EndTime.append(random.randint(1310,1330)) # 퇴실시각 약 22시
#         hour.append(EndTime[i]-StartTime[i])
#     else: #주말일 경우
#         StartTime.append(random.randint(530,550)) # 입실시각 약 9시
#         EndTime.append(random.randint(1010,1030)) # 퇴실시각 약 17시
#         hour.append(EndTime[i]-StartTime[i])
# for i in range(1000):
#     f.write(str(id[2])+','+str(dow[i])+','+str(StartTime[i])+','+str(EndTime[i])+'\n')
#     # f.write(str(id[2])+','+str(dow[i])+','+str(hour[i])+','+str(StartTime[i])+','+str(EndTime[i])+'\n')

# dow=[]
# hour=[]
# StartTime=[]
# EndTime=[]


# # user4
# # 주중에 19시쯤 입실, 주말에 13시쯤 입실
# # 일~목 21시쯤 퇴실, 금~토 23시쯤 퇴실
# for i in range(1000):
#     dow.append(random.randint(1,7))
#     if dow[i]<=5: #평일일 경우
#         StartTime.append(random.randint(1120,1160)) # 입실시각 약 19시
#         if dow[i]==5: #금요일     
#             EndTime.append(random.randint(1360,1400)) # 퇴실시각 약 23시
#             hour.append(EndTime[i]-StartTime[i])                
#         else: #월~목
#             EndTime.append(random.randint(1240,1280)) # 퇴실시각 약 21시
#             hour.append(EndTime[i]-StartTime[i])   
#     else: #주말일 경우
#         StartTime.append(random.randint(520,560)) # 입실시각 약 9시
#         if dow[i]==6: #토요일
#             EndTime.append(random.randint(1360,1400)) # 퇴실시각 약 23시
#             hour.append(EndTime[i]-StartTime[i])
#         else: #일요일
#             EndTime.append(random.randint(1240,1280)) # 퇴실시각 약 21시
#             hour.append(EndTime[i]-StartTime[i])     
# for i in range(1000):
#     f.write(str(id[3])+','+str(dow[i])+','+str(hour[i])+','+str(StartTime[i])+','+str(EndTime[i])+'\n')

# dow=[]
# hour=[]
# StartTime=[]
# EndTime=[]


# # user5
# # 주중에 3시간, 주말에 6시간정도 이용.
# for i in range(1000):
#     dow.append(random.randint(1,7))
#     if dow[i]<=5: #평일일 경우
#         StartTime.append(random.randint(420,1240)) # 입실시각 범위 7~21시
#         hour.append(random.randint(160,200)) # 사용시간 약 3시간
#         EndTime.append(StartTime[i]+hour[i]) 
#     else: #주말일 경우
#         StartTime.append(random.randint(420,1060)) # 입실시각 범위 7~18시
#         hour.append(random.randint(340,380)) # 사용시간 약 6시간
#         EndTime.append(StartTime[i]+hour[i]) 
# for i in range(1000):
#     f.write(str(id[4])+','+str(dow[i])+','+str(hour[i])+','+str(StartTime[i])+','+str(EndTime[i])+'\n')

f.close()