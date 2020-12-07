class RoomModel {
  final int id;
  final int hotelId;
  final int number;
  final int floor;
  final int sleepingPlaces;
  final String description;
  final int rooms;

  RoomModel(
    this.id,
    this.hotelId,
    this.description,
    this.number,
    this.floor,
    this.sleepingPlaces,
    this.rooms,
  );

  RoomModel.fromJson(Map<String, dynamic> json)
      : id = json['id'],
        hotelId = json['hotelId'],
        number = json['number'],
        floor = json['floor'],
        sleepingPlaces = json['sleepingPlaces'],
        description = json['description'],
        rooms = json['rooms'];

  Map<String, dynamic> toJson() => {
        'id': id,
        'hotelId': hotelId,
        'number': number,
        'floor': floor,
        'sleepingPlaces': sleepingPlaces,
        'description': description,
        'rooms': rooms,
      };
}
