import 'package:busapp/screens/main_screen.dart';
import 'package:busapp/services/api/http.dart';
import 'package:busapp/services/parser.dart';
import 'package:flutter/material.dart';
import 'package:busapp/services/user.dart' as user;

class LoginScreen extends StatefulWidget {
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  TextEditingController _loginController;

  TextEditingController _passwordController;

  HTTP _api;

  @override
  void initState() {
    _loginController = TextEditingController();
    _passwordController = TextEditingController();
    _api = HTTP();

    super.initState();
  }

  @override
  void dispose() {
    _loginController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          TextFormField(
            controller: _loginController,
            decoration: InputDecoration(labelText: "login"),
          ),
          TextFormField(
            controller: _passwordController,
            obscureText: true,
            decoration: InputDecoration(labelText: "password"),
          ),
          RaisedButton(
            onPressed: () {
              _api
                  .login(
                username: _loginController.text,
                password: _passwordController.text,
              )
                  .then((value) {
                String bearer = value.headers.value('authorization');
                String jwt = bearer.replaceAll("Bearer ", "");
                JWTParser jwtParser = JWTParser();
                Map<String, dynamic> algo = jwtParser.parseJwtHeader(jwt);
                Map<String, dynamic> jwtPayload =
                    jwtParser.parseJwtPayLoad(jwt);
                user.username = jwtPayload['sub'];
                user.jwt = jwt;

                Navigator.push(context,
                    MaterialPageRoute(builder: (context) => MainScreen()));
              });
            },
            child: Text('Login'),
          )
        ],
      ),
    );
  }
}
