import urllib.request
import time
import emoji
import re
import uuid
import os
import os.path
import urllib.request
import lxml.etree
import requests

import lxml.etree
from flask import Flask
# 导入扩展包flask_sqlalchemy
from flask_sqlalchemy import SQLAlchemy
# encoding: utf-8
import random
from sqlalchemy.orm import load_only

app = Flask(__name__)
# 配置数据库的连接信息
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:root@localhost/graduation'
# 展示sql语句
app.config['SQLALCHEMY_ECHO'] = True
# 关闭动态追踪修改的警告信息
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

# 爬虫数据收集
# 豆瓣书籍分类数据
URL_ALL = [
    ["https://book.douban.com/tag/%E5%B0%8F%E8%AF%B4","https://book.douban.com/tag/%E6%96%87%E5%AD%A6","https://book.douban.com/tag/%E5%A4%96%E5%9B%BD%E6%96%87%E5%AD%A6","https://book.douban.com/tag/%E7%BB%8F%E5%85%B8","https://book.douban.com/tag/%E4%B8%AD%E5%9B%BD%E6%96%87%E5%AD%A6"],  # 小说的五个分类
    ["https://book.douban.com/tag/%E6%BC%AB%E7%94%BB","https://book.douban.com/tag/%E6%8E%A8%E7%90%86","https://book.douban.com/tag/%E7%BB%98%E6%9C%AC","https://book.douban.com/tag/%E6%82%AC%E7%96%91","https://book.douban.com/tag/%E7%A7%91%E5%B9%BB"],
    ["https://book.douban.com/tag/%E5%8E%86%E5%8F%B2","https://book.douban.com/tag/%E5%BF%83%E7%90%86%E5%AD%A6","https://book.douban.com/tag/%E5%93%B2%E5%AD%A6","https://book.douban.com/tag/%E7%A4%BE%E4%BC%9A%E5%AD%A6","https://book.douban.com/tag/%E4%BC%A0%E8%AE%B0"],
    ["https://book.douban.com/tag/%E7%94%9F%E6%B4%BB","https://book.douban.com/tag/%E7%88%B1%E6%83%85","https://book.douban.com/tag/%E6%88%90%E9%95%BF","https://book.douban.com/tag/%E5%BF%83%E7%90%86","https://book.douban.com/tag/%E6%97%85%E8%A1%8C"],
    ["https://book.douban.com/tag/%E7%BB%8F%E6%B5%8E%E5%AD%A6","https://book.douban.com/tag/%E7%AE%A1%E7%90%86","https://book.douban.com/tag/%E5%95%86%E4%B8%9A","https://book.douban.com/tag/%E9%87%91%E8%9E%8D","https://book.douban.com/tag/%E6%8A%95%E8%B5%84"],
    ["https://book.douban.com/tag/%E7%A7%91%E6%99%AE","https://book.douban.com/tag/%E4%BA%92%E8%81%94%E7%BD%91","https://book.douban.com/tag/%E7%A7%91%E5%AD%A6","https://book.douban.com/tag/%E7%BC%96%E7%A8%8B","https://book.douban.com/tag/%E7%AE%97%E6%B3%95"]
    ]

# 实例化sqlalchemy对象，并且和程序实例关联
db = SQLAlchemy(app)

# 书籍类型
class Book(db.Model):
    __tablename__ = 'book'

    id = db.Column("id",db.Integer,primary_key=True,autoincrement=True,doc="书籍主键")
    name = db.Column ("name",db.String,doc="书籍名称")
    author = db.Column ("author",db.String,doc="书籍作者")
    detail = db.Column("detail",db.String,doc="书籍详情")
    price = db.Column ("price",db.String,doc="书籍价格")
    picture_URL = db.Column ("picture_URL",db.String,doc="书籍照片路径")
    rate = db.Column ("rate",db.Float,doc="书籍评分")
    classBook1 = db.Column ("bclass1",db.Integer,doc="读书类别")
    classBook2 = db.Column("bclass2", db.Integer, doc="细读书类别")

# 用户数据类
class User(db.Model):
    __tablename__ = 'user'
    id = db.Column("id",db.Integer,primary_key = True,autoincrement = True)
    name = db.Column("userName",db.String,nullable = False)
    age = db.Column("userAge",db.Integer,nullable = False)
    userId = db.Column("userId",db.String,nullable = False)
    sex = db.Column("userSex",db.Integer,nullable = False)
    picture = db.Column("userPicture",db.String,nullable = False)
    email = db.Column("userEmail",db.String)

# 用户历史数据类
class History (db.Model):
    __tablename__ = 'history'
    id = db.Column("id",db.Integer,primary_key = True,autoincrement = True)
    userId = db.Column("userId",db.String,nullable = False)
    time = db.Column("date",db.DateTime,nullable = False)
    bookId = db.Column("bookId",db.String,nullable = False)
    bclass1 = db.Column("bclass1",db.Integer,nullable = False) # 类别一
    bclass2 = db.Column("bclass2",db.Integer,nullable = False) # 类别二


@app.route("/generate_user_history")
def generate_user_history():
    bookIds = list(Book.query.with_entities(Book.id).all())
    userIds = list(User.query.with_entities(User.id).all())
    bclass1s = [1,2,3,4,5,6]
    bclass2s = [1,2,3,4,5]
    for i in range(20000):
        history = History(userId=random.choice(userIds)[0],bookId=random.choice(bookIds)[0],time=time.strftime('%Y-%m-%d', time.localtime()),bclass1=random.sample(bclass1s,1)[0],bclass2=random.sample(bclass2s,1)[0])
        db.session.add(history)
        db.session.commit()
    return "OK"

@app.route('/generate_user_data')
def generate_user_data():
    first_name = ["王", "李", "张", "刘", "赵", "蒋", "孟", "陈", "徐", "杨", "沈", "马", "高", "殷", "上官", "钟",
                  "常"]
    second_name = ["伟", "华", "建国", "洋", "刚", "万里", "爱民", "牧", "陆", "路", "昕", "鑫", "兵", "硕", "志宏",
                   "峰", "磊", "雷", "文", "明浩", "光", "超", "军", "达"]
    sufix = ["@qq.com","@126.com","@gmail.com","@yahoo.com","@foxmail.com"]
    for i in range(200):
        name = random.choice(first_name) + random.choice(second_name)
        userId = str(random.randint(1,1000)) + str(random.randint(1,1000)) + str(random.randint(1,1000))
        sex = random.randint(1,2)
        age = random.randint(20,60)
        picture = "E:\\graduation\\idea\\graduate_program\\UserData" + "\\default.jpg"
        email = str(random.randint(1000,9999))  + str(random.randint(1000,9999)) + random.choice(sufix)
        user = User(name=name,age=age,userId=userId,sex=sex,picture=picture,email=email)
        db.session.add(user)
        db.session.commit()
    return "OK"

# 爬虫数据并且插入到数据库
@app.route("/insert")
def BOOKINSERT():
    for m in range(len(URL_ALL)):
        os.makedirs('E:\\graduation\\idea\\graduate_program\\T\\Data\\' + str(m + 1))
        for n in range(len(URL_ALL[m])):
            for i in range(10):
                url = str(URL_ALL[m][n]) + "?start=" + str(i * 20)
                headers = {
                    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.60 Safari/537.36 Edg/100.0.1185.29'
                }
                request = urllib.request.Request(url=url, headers=headers)
                response = urllib.request.urlopen(request)
                content = response.read().decode("utf8")
                tree = lxml.etree.HTML(content)
                res_author = list(
                    tree.xpath("//li[@class='subject-item']//div[@class='info']//div[@class='pub']/text()"))
                res_detail = list(tree.xpath("//li[@class='subject-item']//div[@class='info']//p/text()"))
                res_name = list(tree.xpath("//li[@class='subject-item']//div[@class='info']//h2//a/@title"))
                res_price = list(tree.xpath("//li[@class='subject-item']//span[@class='buy-info']/a/text()"))
                res_pic = list(tree.xpath("//li[@class='subject-item']//div[@class='pic']//img/@src"))
                res_rate = list(tree.xpath("//li[@class='subject-item']//span[@class='rating_nums']/text()"))
                if os.path.exists('E:\\graduation\\idea\\graduate_program\\T\\Data\\' + str(m + 1) + '\\' + str(n + 1) + '\\'):
                    for j in range(len(res_author)):
                        if not str(res_name[j]).startswith("《") and str(res_name[j]).find("/"):
                            booK_name = str(uuid.uuid4())
                            Book_URL = '\\T\\Data\\' + str(m + 1) + '\\' + str(n+1) +'\\' + booK_name + ".png"
                            try:
                                if not str(res_name[j]).startswith("《") and str(res_name[j]).find("/"):
                                    fileURL = 'E:\\graduation\\idea\\graduate_program\\T\\Data\\' + str(m + 1) + '\\' + str(
                                        n + 1) + '\\' + booK_name + ".png"
                                    print(fileURL)
                                    r = requests.get(res_pic[j].strip(" "))
                                    with open(fileURL, 'wb') as f:
                                        f.write(r.content)
                            except:
                                continue
                            Book_NAME = str(res_name[j]).strip(" ")
                            print(Book_NAME)
                            Book_Author = str(res_author[j])[6:].strip(" ").strip("\n").strip(' ').strip("\n").strip(" ")
                            if j < len(res_rate):
                                Book_RATE = float(res_rate[j])
                            else:
                                Book_RATE = 9.0
                            if j < len(res_price):
                                Book_PRICE = str(res_price[j]).strip(" ").strip("\n").strip(" ")
                            else:
                                Book_PRICE = ""
                            if j < len(res_detail):
                                Book_DETAIL = re.sub('(\:.*?\:)', '', emoji.demojize(str(res_detail[j])))
                            else:
                                Book_DETAIL = ""
                            book_in = Book(name=Book_NAME, author=Book_Author, price=Book_PRICE, rate=Book_RATE,detail=Book_DETAIL,
                                           picture_URL=Book_URL, classBook1=m + 1,classBook2=n + 1)
                            db.session.add(book_in)
                            db.session.commit()
                else:
                    os.makedirs('E:\\graduation\\idea\\graduate_program\\T\\Data\\' + str(m + 1) + '\\' + str(n+1) +'\\')

    return "OK"
if __name__ == '__main__':
    app.run()