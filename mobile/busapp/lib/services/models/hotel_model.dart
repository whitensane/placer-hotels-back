class HotelModel {
  final int id;
  final String title;
  final String address;
  final String phone;
  final String email;
  final int stars;

  HotelModel({
    this.id,
    this.title,
    this.address,
    this.phone,
    this.email,
    this.stars,
  });

  HotelModel.fromJson(Map<String, dynamic> json)
      : id = json['id'],
        title = json['title'],
        address = json['address'],
        phone = json['phone'],
        email = json['email'],
        stars = json['stars'];

  Map<String, dynamic> toJson() => {
        'id': id,
        'title': title,
        'address': address,
        'phone': phone,
        'email': email,
        'stars': stars,
      };

  @override
  String toString() {
    return '''id: $id, title: $title, address: $address, phone: $phone, email: $email, stars: $stars''';
  }
}
