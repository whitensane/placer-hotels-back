import 'package:busapp/screens/login_screen.dart';
import 'package:busapp/screens/main_screen.dart';
import 'package:busapp/services/api/http.dart';
import 'package:busapp/services/parser.dart';
import 'package:flutter/material.dart';
import 'package:busapp/services/user.dart' as user;

class RegisterScreen extends StatefulWidget {
  @override
  _RegisterScreenState createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  TextEditingController _loginController;

  TextEditingController _passwordController;
  TextEditingController _passwordConfirmController;

  HTTP _api;

  @override
  void initState() {
    _loginController = TextEditingController();
    _passwordController = TextEditingController();
    _passwordConfirmController = TextEditingController();
    _api = HTTP();

    super.initState();
  }

  @override
  void dispose() {
    _loginController.dispose();
    _passwordController.dispose();
    _passwordConfirmController.dispose();
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
          TextFormField(
            controller: _passwordConfirmController,
            obscureText: true,
            decoration: InputDecoration(labelText: "password confirm"),
          ),
          RaisedButton(
            onPressed: () {
              _api
                  .register(
                username: _loginController.text,
                password: _passwordController.text,
              )
                  .then((value) {
                if (value.statusCode == 201)
                  Navigator.push(context,
                      MaterialPageRoute(builder: (context) => LoginScreen()));
              });
            },
            child: Text('Register'),
          )
        ],
      ),
    );
  }
}
