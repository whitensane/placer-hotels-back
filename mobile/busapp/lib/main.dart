import 'package:busapp/screens/intro_screen.dart';
import 'package:flutter/material.dart';

import 'screens/login_screen.dart';

void main() async {
  runApp(JavaFront());
}

class JavaFront extends StatefulWidget {
  @override
  _JavaFrontState createState() => _JavaFrontState();
}

class _JavaFrontState extends State<JavaFront> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Java Front',
      theme: ThemeData(
        primarySwatch: Colors.grey,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: IntroScreen(),
    );
  }
}
