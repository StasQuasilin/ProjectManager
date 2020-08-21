import 'package:flutter/cupertino.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:targeton_app/pages/AppPage.dart';

class TreePage extends AppPage{



  @override
  State<StatefulWidget> createState() {
    return _TreePageState();
  }

  @override
  String getTitle() {
    return "TreePage";
  }

}

class _TreePageState extends State<TreePage> {
  @override
  Widget build(BuildContext context) {
    return Text("Tree Page");
  }
}