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
   [git clone (https://github.com/AnhkhoaBeoz/WarningTraffic)]]
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
# Hướng dẫn Cài đặt Thư viện

## Nhóm 1: Kotlin và Coroutines
- `org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9`
- `org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4`

## Nhóm 2: Firebase Authentication và Realtime Database
- `com.google.android.gms:play-services-auth:20.7.0`
- `com.google.firebase:firebase-bom:32.2.3`
- `com.google.firebase:firebase-auth-ktx`
- `com.google.firebase:firebase-database-ktx`

## Nhóm 3: AndroidX
- `androidx.fragment:fragment-ktx:1.6.1`
- `androidx.core:core-ktx:1.12.0`
- `androidx.appcompat:appcompat:1.6.1`

## Nhóm 4: Material Design
- `com.google.android.material:material:1.10.0`

## Nhóm 5: Constraint Layout
- `androidx.constraintlayout:constraintlayout:2.1.4`

## Nhóm 6: Firebase Analytics và Crashlytics
- `com.google.firebase:firebase-crashlytics:18.4.3`
- `com.google.firebase:firebase-analytics:21.3.0`

## Nhóm 7: Google Play Services và Location
- `com.google.android.gms:play-services-location:21.0.1`

## Nhóm 8: Google Places
- `com.google.android.libraries.places:places:3.2.0`

## Nhóm 9: Testing
- `junit:junit:4.13.2`
- `androidx.test.ext:junit:1.1.5`
- `androidx.test.espresso:espresso-core:3.5.1`

## Nhóm 10: Multidex
- `androidx.multidex:multidex:2.0.1`

## Nhóm 11: Glide và CircleImageView
- `com.github.bumptech.glide:glide:4.16.0`
- `de.hdodenhof:circleimageview:3.1.0`

## Nhóm 12: Klaxon (JSON Parser)
- `com.beust:klaxon:5.5`

## Nhóm 13: Google Maps
- `com.github.jd-alexander:library:1.1.0`
- `com.google.android.gms:play-services-maps:18.1.0`
- `com.google.maps.android:android-maps-utils:3.5.3`


