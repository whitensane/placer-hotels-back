class RoomFeedbackModel {
  final int id;
  final int hotelId;
  final int roomId;
  final String title;
  final String description;
  final int rating;
  final int userId;

  RoomFeedbackModel(
    this.id,
    this.hotelId,
    this.roomId,
    this.title,
    this.description,
    this.rating,
    this.userId,
  );

  RoomFeedbackModel.fromJson(Map<String, dynamic> json)
      : id = json['id'],
        hotelId = json['hotelId'],
        roomId = json['roomId'],
        title = json['title'],
        description = json['description'],
        rating = json['rating'],
        userId = json['userId'];

  Map<String, dynamic> toJson() => {
        'id': id,
        'hotelId': hotelId,
        'roomId': roomId,
        'title': title,
        'description': description,
        'rating': rating,
        'userId': userId
      };
}
