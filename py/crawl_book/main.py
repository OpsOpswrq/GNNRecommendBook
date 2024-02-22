# 获取大量图书信息
import os.path
import urllib.request
import lxml.etree
import requests
book_name = []
book_detail = []
book_price = []
URL_ALL = [
    ["https://book.douban.com/tag/%E5%B0%8F%E8%AF%B4","https://book.douban.com/tag/%E6%96%87%E5%AD%A6","https://book.douban.com/tag/%E5%A4%96%E5%9B%BD%E6%96%87%E5%AD%A6","https://book.douban.com/tag/%E7%BB%8F%E5%85%B8","https://book.douban.com/tag/%E4%B8%AD%E5%9B%BD%E6%96%87%E5%AD%A6"],  # 小说的五个分类
    ["https://book.douban.com/tag/%E6%BC%AB%E7%94%BB","https://book.douban.com/tag/%E6%8E%A8%E7%90%86","https://book.douban.com/tag/%E7%BB%98%E6%9C%AC","https://book.douban.com/tag/%E6%82%AC%E7%96%91","https://book.douban.com/tag/%E7%A7%91%E5%B9%BB"],
    ["https://book.douban.com/tag/%E5%8E%86%E5%8F%B2","https://book.douban.com/tag/%E5%BF%83%E7%90%86%E5%AD%A6","https://book.douban.com/tag/%E5%93%B2%E5%AD%A6","https://book.douban.com/tag/%E7%A4%BE%E4%BC%9A%E5%AD%A6","https://book.douban.com/tag/%E4%BC%A0%E8%AE%B0"],
    ["https://book.douban.com/tag/%E7%94%9F%E6%B4%BB","https://book.douban.com/tag/%E7%88%B1%E6%83%85","https://book.douban.com/tag/%E6%88%90%E9%95%BF","https://book.douban.com/tag/%E5%BF%83%E7%90%86","https://book.douban.com/tag/%E6%97%85%E8%A1%8C"],
    ["https://book.douban.com/tag/%E7%BB%8F%E6%B5%8E%E5%AD%A6","https://book.douban.com/tag/%E7%AE%A1%E7%90%86","https://book.douban.com/tag/%E5%95%86%E4%B8%9A","https://book.douban.com/tag/%E9%87%91%E8%9E%8D","https://book.douban.com/tag/%E6%8A%95%E8%B5%84"],
    ["https://book.douban.com/tag/%E7%A7%91%E6%99%AE","https://book.douban.com/tag/%E4%BA%92%E8%81%94%E7%BD%91","https://book.douban.com/tag/%E7%A7%91%E5%AD%A6","https://book.douban.com/tag/%E7%BC%96%E7%A8%8B","https://book.douban.com/tag/%E7%AE%97%E6%B3%95"]
    ]
def get_content(cnt):
    for m in range(len(URL_ALL)):
        os.makedirs('E:\\graduation\\idea\\graduate_program\\T\\Data\\' + str(m + 1))
        for n in range(len(URL_ALL[m])):
            for i in range(cnt):
                url = str(URL_ALL[m][n]) + "?start=" + str(i * 20)
                headers = {
                    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.60 Safari/537.36 Edg/100.0.1185.29'
                }
                request = urllib.request.Request(url=url, headers=headers)
                response = urllib.request.urlopen(request)
                content = response.read().decode("utf8")
                tree = lxml.etree.HTML(content)
                res_detail = list(
                    tree.xpath("//li[@class='subject-item']//div[@class='info']//div[@class='pub']/text()"))
                res_name = list(tree.xpath("//li[@class='subject-item']//div[@class='info']//h2//a/@title"))
                res_price = list(tree.xpath("//li[@class='subject-item']//span[@class='buy-info']/a/text()"))
                res_pic = list(tree.xpath("//li[@class='subject-item']//div[@class='pic']//img/@src"))
                res_rating = list(tree.xpath("//li[@class='subject-item']//span[@class='rating_nums']/text()"))
                if os.path.exists('E:\\graduation\\idea\\graduate_program\\T\\Data\\' + str(m + 1) + '\\' + str(n+1) +'\\'):
                    for j in range(len(res_detail)):
                        try:
                            if not str(res_name[j]).startswith("《") and str(res_name[j]).find("/"):
                                book_name.append(str(res_name[j]).strip(" "))
                                fileURL = 'E:\\graduation\\idea\\graduate_program\\T\\Data\\' + str(m + 1) + '\\' + str(
                                    n + 1) + '\\' + str(res_name[j]).strip(" ") + ".png"
                                print(fileURL)
                                book_detail.append(
                                    str(res_detail[j])[6:].strip(" ").strip("\n").strip(' ').strip("\n").strip(" "))
                                r = requests.get(res_pic[j].strip(" "))
                                with open(fileURL, 'wb') as f:
                                    f.write(r.content)
                                if j < len(res_price):
                                    book_price.append(str(res_price[j]).strip(" "))
                                else:
                                    book_price.append("纸质版 29.00元")
                        except:
                            continue
                else:
                    os.makedirs('E:\\graduation\\idea\\graduate_program\\T\\Data\\' + str(m + 1) + '\\' + str(n+1) +'\\')
if __name__ == '__main__':
    get_content(10)