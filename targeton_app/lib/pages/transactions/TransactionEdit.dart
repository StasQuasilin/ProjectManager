import 'package:flutter/material.dart';
import 'package:targeton_app/pages/AppPage.dart';

class TransactionEdit extends AppPage{

  TransactionEdit(myHomePageState);

  @override
  State<StatefulWidget> createState() {
    return _TransactionEditState();
  }

  @override
  String getTitle() {
    return "Transaction Edit";
  }
}

class _TransactionEditState extends State<TransactionEdit> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          children: [
            Text("transaction EDIT")
          ],
        ),
      ),
    );
  }
}