import 'dart:convert';

import 'package:busapp/services/api/http.dart';
import 'package:busapp/services/models/hotel_feedback_model.dart';
import 'package:busapp/services/models/room_model.dart';
import 'package:busapp/widgets/app_bar.dart';
import 'package:flutter/material.dart';

class RoomScreen extends StatefulWidget {
  final int roomId;
  final int hotelId;
  const RoomScreen({
    Key key,
    @required this.roomId,
    @required this.hotelId,
  }) : super(key: key);
  @override
  _RoomScreenState createState() => _RoomScreenState();
}

class _RoomScreenState extends State<RoomScreen> {
  HTTP _api;
  RoomModel _room;

  List<RoomFeedbackModel> feedbacks = [];

  @override
  void initState() {
    _api = HTTP();

    _api.getRoom(widget.roomId).then((value) {
      if (value.data.isNotEmpty) {
        json.decode(value.data['feedbacks']).forEach((feedback) {
          feedbacks.add(RoomFeedbackModel.fromJson(feedback));
        });
        setState(() {
          _room = RoomModel.fromJson(value.data['rooms']);
        });
      }
    });

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PlacerAppBar(
        title: FlatButton(
          child: Text('add feedback to room'),
          onPressed: () {
            _api.setRoomFeedback(
              hotelId: widget.hotelId,
              roomId: widget.roomId,
              title: 'Title',
              description: 'Description',
              rating: 5,
              userId: 1,
            );
          },
        ),
      ),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _room != null
                ? Container(
                    margin: EdgeInsets.all(10),
                    padding: EdgeInsets.all(10),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          'Info about hotel',
                          style: TextStyle(
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                        Text('Number: ' + _room.number.toString()),
                        Text('Description: ' + _room.description.toString()),
                        Text('Floor: ' + _room.floor.toString()),
                        Text('Rooms: ' + _room.rooms.toString()),
                        Text('SleepingPlaces: ' +
                            _room.sleepingPlaces.toString()),
                      ],
                    ),
                  )
                : SizedBox(height: 0, width: 0),
            ListView.separated(
              shrinkWrap: true,
              itemCount: feedbacks.length,
              physics: NeverScrollableScrollPhysics(),
              itemBuilder: (BuildContext context, int index) {
                return Container(
                  margin: EdgeInsets.all(10),
                  padding: EdgeInsets.all(10),
                  decoration: BoxDecoration(
                    color: Colors.grey,
                    borderRadius: BorderRadius.all(
                      Radius.circular(20.0),
                    ),
                  ),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text('Feedback number: ${feedbacks[index].id}'),
                      Text('Feedback by: ${feedbacks[index].userId}'),
                      Text('Feedback to room: ${feedbacks[index].roomId}'),
                      Text('Feedback title: ${feedbacks[index].title}'),
                      Text(
                          'Feedback description: ${feedbacks[index].description}'),
                      Row(
                        children: [
                          Text('Feedback rating: ${feedbacks[index].rating}'),
                          Container(
                            height: 20,
                            child: ListView.builder(
                              shrinkWrap: true,
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
                        ],
                      ),
                    ],
                  ),
                );
              },
              separatorBuilder: (BuildContext context, int index) {
                return Text('');
              },
            ),
          ],
        ),
      ),
    );
  }
}
