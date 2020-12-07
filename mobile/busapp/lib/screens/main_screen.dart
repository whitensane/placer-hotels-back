import 'package:busapp/screens/hotel_screen.dart';
import 'package:busapp/services/api/http.dart';
import 'package:busapp/services/models/hotel_model.dart';
import 'package:busapp/widgets/app_bar.dart';
import 'package:flutter/material.dart';
import 'package:shimmer/shimmer.dart';

class MainScreen extends StatefulWidget {
  @override
  _MainScreenState createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  String welcomeString = 'Welcome to hotels';
  String selectHotel = 'Please, select a hotel';

  HTTP _api;
  List<HotelModel> hotels = [];

  @override
  void initState() {
    super.initState();

    _api = HTTP();

    _api.getHotels().then((value) {
      value.data.forEach((hotel) {
        setState(() {
          hotels.add(HotelModel.fromJson(hotel));
        });
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PlacerAppBar(
        title: FlatButton(
          child: Text('add hotel'),
          onPressed: () {
            _api.setHotel(
                address: 'Manasa str java',
                email: 'email@java.ead',
                phone: '8707',
                stars: 4,
                title: 'JavaHotel');
          },
        ),
      ),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(welcomeString),
            Text(selectHotel),
            GridView.count(
              crossAxisCount: 2,
              crossAxisSpacing: 10.0,
              mainAxisSpacing: 10.0,
              physics: NeverScrollableScrollPhysics(),
              shrinkWrap: true,
              children: List.generate(
                hotels.length,
                (index) {
                  HotelModel hotel = hotels[index];
                  return hotels.isEmpty
                      ? Container(
                          width: 50,
                          height: 50,
                          child: Shimmer.fromColors(
                            baseColor: Colors.grey[200],
                            highlightColor: Colors.grey[300],
                            child: Container(
                              width: 50,
                              height: 50,
                              decoration: BoxDecoration(
                                color: Colors.grey,
                                borderRadius: BorderRadius.all(
                                  Radius.circular(20.0),
                                ),
                              ),
                              child: Padding(
                                padding: EdgeInsets.all(10.0),
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  children: [
                                    Container(
                                      height: 1,
                                      child: ListView.builder(
                                        scrollDirection: Axis.horizontal,
                                        physics: NeverScrollableScrollPhysics(),
                                        itemCount: 5,
                                        itemBuilder: (context, index) {
                                          return Icon(
                                            Icons.star,
                                            color: Colors.black,
                                          );
                                        },
                                      ),
                                    ),
                                    Column(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                      children: [],
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          ),
                        )
                      : Padding(
                          padding: EdgeInsets.all(10.0),
                          child: InkWell(
                            onTap: () {
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => HotelScreen(
                                    hotelId: hotel.id,
                                    hotelTitle: hotel.title,
                                  ),
                                ),
                              );
                            },
                            child: Container(
                              width: 50,
                              height: 50,
                              decoration: BoxDecoration(
                                color: Colors.grey,
                                borderRadius: BorderRadius.all(
                                  Radius.circular(20.0),
                                ),
                              ),
                              child: Padding(
                                padding: EdgeInsets.all(10.0),
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  children: [
                                    Text(
                                      hotel.title,
                                    ),
                                    Container(
                                      height: 1,
                                      child: ListView.builder(
                                        scrollDirection: Axis.horizontal,
                                        physics: NeverScrollableScrollPhysics(),
                                        itemCount: 5,
                                        itemBuilder: (context, index) {
                                          return Icon(
                                            Icons.star,
                                            color: index < hotel.stars
                                                ? Colors.yellow
                                                : Colors.black,
                                          );
                                        },
                                      ),
                                    ),
                                    Column(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                      children: [
                                        Text(
                                          hotel.address,
                                        ),
                                        Text(
                                          hotel.email,
                                        ),
                                        Text(
                                          hotel.phone,
                                        ),
                                      ],
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          ),
                        );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
