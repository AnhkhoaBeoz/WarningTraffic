# WarningTraffic

Ứng dụng giúp người dùng theo dỗi tình trạng giao thông thời gian thực, tìm được. Đồng thời có thể xem được địa điểm đang kẹt xe, cảnh báo khi chạy vào tuyến đường kẹt, báo cáo kẹt xe tại thời điểm đó để cho người dùng khác nhận biết được và sẽ nhận được điểm thưởng. Ứng dụng cũng bao gồm việc học các câu hỏi bằng lái để người dùng có thể học và thi thử bằng lái xe.
# Ứng dụng Cảnh báo Giao thông Thời gian thực



## Mô tả

Ứng dụng Cảnh báo Giao thông Thời gian thực là một ứng dụng di động được phát triển bằng Kotlin trên nền tảng Android. Ứng dụng này cho phép người dùng:

- Theo dõi tình trạng giao thông thời gian thực.
- Xem các địa điểm đang kẹt xe trên bản đồ.
- Nhận cảnh báo khi chạy vào các tuyến đường kẹt.
- Báo cáo tình trạng kẹt xe để giúp người dùng khác.

Ngoài ra, ứng dụng còn cung cấp tính năng học và thi thử bằng lái xe để người dùng có thể chuẩn bị cho bài kiểm tra bằng lái.

## Yêu cầu

Trước khi sử dụng dự án này, bạn cần đảm bảo rằng bạn đã cài đặt:

- Android Studio.
- JDK 8 trở lên.
- Tài khoản Firebase và đã cấu hình dự án Firebase của bạn.

## Cài đặt

1. Clone dự án từ kho lưu trữ:
   ```shell
   [git clone https://github.com/yourusername/your-traffic-app.git](https://github.com/AnhkhoaBeoz/WarningTraffic)https://github.com/AnhkhoaBeoz/WarningTraffic
   Mở dự án bằng Android Studio.

2: Cấu hình tài khoản Firebase của bạn bằng cách thay thế tệp google-services.json trong thư mục app bằng tệp JSON của dự án Firebase của bạn.
3:Cấu hình Google map api 
  3.1: Lấy Khóa API của Google Maps:

-Đăng nhập vào Google Cloud Console.
-Tạo một dự án mới hoặc chọn một dự án đã tồn tại.
-Kích hoạt API Google Maps cho dự án của bạn.
-Tạo một khóa API và hạn chế nó nếu cần thiết (ví dụ: hạn chế bằng IP hoặc ứng dụng Android).
  3.2 Thêm Khóa API vào dự án:

Mở tệp google_maps_api.xml trong thư mục app/src/debug/res/values hoặc app/src/release/res/values.
Thay đổi giá trị google_maps_key bằng khóa API mà bạn vừa tạo.
