import 'package:flutter/material.dart';
import 'package:targeton_app/pages/AppPage.dart';

class GoalPage extends AppPage{



  @override
  State<StatefulWidget> createState() {
    return _GoalPageState();
  }

  @override
  String getTitle() {
    return "GOALS";
  }
}

class _GoalPageState extends State<GoalPage> {
  @override
  Widget build(BuildContext context) {
    return Text("GOAL APGE");
  }
}