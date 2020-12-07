import 'package:busapp/screens/login_screen.dart';
import 'package:busapp/screens/main_screen.dart';
import 'package:busapp/screens/register.dart';
import 'package:busapp/services/api/http.dart';
import 'package:busapp/services/parser.dart';
import 'package:flutter/material.dart';
import 'package:busapp/services/user.dart' as user;

class IntroScreen extends StatefulWidget {
  @override
  _IntroScreenState createState() => _IntroScreenState();
}

class _IntroScreenState extends State<IntroScreen> {
  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Center(
            child: RaisedButton(
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute(builder: (context) => LoginScreen()));
              },
              child: Text('Login'),
            ),
          ),
          RaisedButton(
            onPressed: () {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => RegisterScreen()));
            },
            child: Text('Register'),
          ),
        ],
      ),
    );
  }
}
