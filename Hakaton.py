# -*- coding: utf-8 -*-
import requests
import time
import json
import logging
logging.basicConfig(level=logging.DEBUG)

s = requests.Session()
headers = {'content-type': 'application/json'}
s.headers.update(headers)

"http://45.55.61.171:5050/api/subjects/"
subject_url = "http://45.55.61.171:5050/api/subjects/"

data_ = {
"subjectName": "תנ'ך",
"author": "ארץ ישראל"
}

data_ = json.dumps(data_)
i = 0
start = time.time()

r = s.post(subject_url, data=data_)
print(r.status_code, r.content)

end = time.time()

print('Time: {}. index: {}'.format(end - start, i))

s = requests.Session()
headers = {'content-type': 'application/json'}
s.headers.update(headers)

"http://45.55.61.171:5050/api/subjects/"
add_url = "http://45.55.61.171:5050/api/subjects/2/questions/"
in_file = open('.\questions.txt', "rb")
strRef = in_file.read()
in_file.close()
q_list = strRef.split('##')
i = 0
for q in q_list:
    start = time.time()

    r = s.post(add_url, data=q)
    print(r.status_code, r.content)
    # # session.get(url)
    #
    end = time.time()
    #
    i += 1
    print('Time: {}. index: {}'.format(end - start, i))
    # print(q)

    # print(json.JSONDecoder().decode(q))


data_ = {
"subjectName": "היסטוריה",
"author": "ארץ ישראל"
}

data_ = json.dumps(data_)
i = 0
start = time.time()

r = s.post(subject_url, data=data_)
print(r.status_code, r.content)

end = time.time()

print('Time: {}. index: {}'.format(end - start, i))


add_url1 = "http://45.55.61.171:5050/api/subjects/1/questions/"
in_file = open('.\questions1.txt', "rb")
strRef = in_file.read()
in_file.close()
q_list = strRef.split('##')
i = 0
for q in q_list:
    start = time.time()

    r = s.post(add_url1 , data=q)
    print(r.status_code, r.content)
    # # session.get(url)
    #
    end = time.time()
    #
    i += 1
    print('Time: {}. index: {}'.format(end - start, i))
    # print(q)

    # print(json.JSONDecoder().decode(q))