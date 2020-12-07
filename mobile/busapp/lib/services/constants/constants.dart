class Constants {
  static String roomRoute = 'rooms/';
  static String authRoute = 'auth/';
  static String bookingRoute;
  static String feedbackRoute = 'feedbacks/';
  static String hotelRoute = 'hotels';
  static String localHost = 'http://127.0.0.1:8080/';

  static final Constants _constants = Constants._internal();

  factory Constants() {
    return _constants;
  }

  Constants._internal();
}
