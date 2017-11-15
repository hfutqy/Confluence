//java实现
```
            String urlString = "http://public-api.nj.pla.tuniu.org/fb2/t2/G3/M00/77/04/Cii-a1mtBvKIX1YUAAAoCZWY800AAGS-wOiWbwAACgh41.xlsx";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置3s超时限定
            conn.setConnectTimeout(3 * 1000);
            //得到输入流
            InputStream inputStream = conn.getInputStream();
```
