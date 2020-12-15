import 'package:busapp/screens/room_screen.dart';
import 'package:busapp/services/api/http.dart';
import 'package:busapp/services/models/hotel_feedback_model.dart';
import 'package:busapp/services/models/room_model.dart';
import 'package:busapp/widgets/app_bar.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

class HotelScreen extends StatefulWidget {
  final int hotelId;
  final String hotelTitle;

  const HotelScreen({
    Key key,
    @required this.hotelId,
    @required this.hotelTitle,
  }) : super(key: key);
  @override
  _HotelScreenState createState() => _HotelScreenState();
}

class _HotelScreenState extends State<HotelScreen> {
  HTTP _api;
  List<RoomFeedbackModel> feedbacks = [];
  List<RoomModel> rooms = [];

  @override
  void initState() {
    _api = HTTP();
    _api.getHotel(hotelId: widget.hotelId).then((value) {
      setState(() {
        value.data['feedbacks'].forEach((feedback) {
          feedbacks.add(RoomFeedbackModel.fromJson(feedback));
        });
        value.data['rooms'].forEach((room) {
          rooms.add(RoomModel.fromJson(room));
        });
      });
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PlacerAppBar(
        actions: [
          FlatButton(
            onPressed: () async {
              Response response = await _api.setRoom(
                  description: '',
                  floor: 1,
                  hotelId: widget.hotelId,
                  number: 215,
                  rooms: 5,
                  sleepingPlaces: 5);
            },
            child: Text('addroom'),
          ),
        ],
        title: FlatButton(
          child: Text('add feedvack'),
          onPressed: () async {
            print(await _api.setRoomFeedback(
              description: '5',
              hotelId: widget.hotelId,
              rating: 5,
              roomId: 5,
              title: '5',
              userId: 5,
            ));
          },
        ),
      ),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Rooms'),
            ListView.separated(
              physics: NeverScrollableScrollPhysics(),
              separatorBuilder: (context, index) => Text(''),
              shrinkWrap: true,
              itemCount: rooms.length,
              itemBuilder: (BuildContext context, int index) {
                return Center(
                  child: InkWell(
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => RoomScreen(
                            roomId: rooms[index].id,
                            hotelId: rooms[index].hotelId,
                          ),
                        ),
                      );
                    },
                    child: Container(
                      width: 200,
                      color: Colors.grey[300],
                      child: Center(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              'Room number: ' + rooms[index].number.toString(),
                            ),
                            Text(
                              'Room count: ' + rooms[index].rooms.toString(),
                            ),
                            Text(
                              'Room sleeping places: ' +
                                  rooms[index].sleepingPlaces.toString(),
                            ),
                            Text(
                              'Room description: ' +
                                  rooms[index].description.toString(),
                            ),
                            Text(
                              'Room id: ' + rooms[index].id.toString(),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),
                );
              },
            ),
            Text('Feedbacks to this hotel'),
            ListView.separated(
              separatorBuilder: (context, index) => Text(''),
              physics: NeverScrollableScrollPhysics(),
              shrinkWrap: true,
              itemCount: feedbacks.length,
              itemBuilder: (BuildContext context, int index) {
                return Center(
                  child: Container(
                    width: 200,
                    color: Colors.grey,
                    child: Center(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'Feedback to number: ' +
                                feedbacks[index].roomId.toString(),
                          ),
                          Text(
                            'Feedback title: ' +
                                feedbacks[index].title.toString(),
                          ),
                          Text(
                            'Feedback description: ' +
                                feedbacks[index].description.toString(),
                          ),
                          Container(
                            height: 30,
                            child: ListView.builder(
                              scrollDirection: Axis.horizontal,
                              physics: NeverScrollableScrollPhysics(),
                              itemCount: 5,
                              itemBuilder: (context, i) {
                                return Icon(
                                  Icons.star,
                                  color: i < feedbacks[index].rating
                                      ? Colors.yellow
                                      : Colors.black,
                                );
                              },
                            ),
                          ),
                          Text(
                            'Feedback rating: ' +
                                feedbacks[index].rating.toString(),
                          ),
                          Text(
                            'Feedback by userId: ' +
                                feedbacks[index].userId.toString(),
                          ),
                          Text(
                            'Room id: ' + feedbacks[index].id.toString(),
                          ),
                        ],
                      ),
                    ),
                  ),
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
