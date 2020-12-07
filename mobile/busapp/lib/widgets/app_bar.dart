import 'package:flutter/material.dart';

class PlacerAppBar extends StatelessWidget implements PreferredSizeWidget {
  final Widget title;
  final Widget leading;
  final List<Widget> actions;

  const PlacerAppBar({
    Key key,
    this.title,
    this.leading,
    this.actions,
  }) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return AppBar(
      leading: leading,
      title: title,
      actions: actions,
    );
  }

  @override
  Size get preferredSize => AppBar().preferredSize;
}
