import 'package:busapp/services/constants/constants.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:busapp/services/user.dart' as user;

class HTTP {
  String localhost = Constants.localHost;

  Future<Response> getRequest({
    String path,
    Map<String, dynamic> queryParameters,
  }) async {
    try {
      Options options = Options();
      options.headers["Authorization"] = "Bearer " + user.jwt;
      Response response = await Dio().get(
        path,
        options: options,
        queryParameters: queryParameters,
      );
      print(response.statusCode);
      print(response.data);
      return response;
    } on DioError catch (e) {
      print(e.response.statusCode);
      return null;
    }
  }

  Future<Response> postRequest({
    String path,
    Map<String, dynamic> data,
  }) async {
    try {
      Options options = Options();
      if (user.jwt != null)
        options.headers["Authorization"] = "Bearer " + user.jwt;
      Response response = await Dio().post(
        path,
        options: options,
        data: data,
      );
      print(response.statusCode);
      print(response.data);
      return response;
    } on DioError catch (e) {
      print(e.response.statusCode);
      return null;
    }
  }

  Future<Response> getHotels() {
    String path = localhost + Constants.hotelRoute;
    return getRequest(path: path);
  }

  Future<Response> getHotel({@required int hotelId}) {
    String path = localhost + Constants.hotelRoute + '/id';
    Map<String, dynamic> queryParameters = {"id": hotelId};
    return getRequest(path: path, queryParameters: queryParameters);
  }

  Future<Response> login({
    @required String username,
    @required String password,
  }) {
    String path = localhost + Constants.authRoute + 'signin';
    Map<String, dynamic> data = {
      "username": username,
      "password": password,
    };
    return postRequest(path: path, data: data);
  }

  Future<Response> register({
    @required String username,
    @required String password,
  }) {
    String path = localhost + Constants.authRoute + 'signup';
    Map<String, dynamic> data = {
      "username": username,
      "password": password,
    };
    return postRequest(path: path, data: data);
  }

  Future<Response> setHotel({
    @required String title,
    @required int stars,
    @required String address,
    @required String phone,
    @required String email,
  }) {
    String path = localhost + Constants.hotelRoute;
    Map<String, dynamic> data = {
      "title": title,
      "stars": stars,
      "address": address,
      "phone": phone,
      "email": email,
    };
    return postRequest(path: path, data: data);
  }

  Future<Response> setRoom({
    @required int hotelId,
    @required int number,
    @required int floor,
    @required int sleepingPlaces,
    @required String description,
    @required int rooms,
  }) {
    String path = localhost + Constants.roomRoute;

    Map<String, dynamic> data = {
      'hotelId': hotelId,
      'number': number,
      'floor': floor,
      'sleepingPlaces': sleepingPlaces,
      'description': description,
      'rooms': rooms,
    };

    return postRequest(path: path, data: data);
  }

  Future<Response> setRoomFeedback({
    @required int hotelId,
    @required int roomId,
    @required String title,
    @required String description,
    @required int rating,
    @required int userId,
  }) {
    String path = localhost + Constants.feedbackRoute + 'room';

    Map<String, dynamic> data = {
      'hotelId': hotelId,
      'roomId': roomId,
      'title': title,
      'description': description,
      'rating': rating,
      'userId': userId,
    };

    return postRequest(path: path, data: data);
  }

  Future<Response> getRoom(int roomId) {
    String path = localhost + Constants.roomRoute + 'id';
    Map<String, dynamic> queryParameters = {"id": roomId};
    return getRequest(path: path, queryParameters: queryParameters);
  }
}
