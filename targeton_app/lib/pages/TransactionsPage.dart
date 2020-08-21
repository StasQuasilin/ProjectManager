import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:targeton_app/pages/AppPage.dart';
import 'package:targeton_app/pages/transactions/TransactionEdit.dart';

class TransactionsPage extends AppPage{

  final myHomePageState;

  TransactionsPage(this.myHomePageState);

  @override
  State<StatefulWidget> createState() {
    return _TransactionsPageState(myHomePageState);
  }

  @override
  String getTitle() {
    return "transactions";
  }

}

class _TransactionsPageState extends State<TransactionsPage>{

  final myHomePageState;

  _TransactionsPageState(this.myHomePageState);


  void _newTransaction(){
    myHomePageState.loadPage(new TransactionEdit(myHomePageState));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          children: [
            Text("OlO")
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _newTransaction,
        child: Icon(Icons.add),
      ),
    );
  }
}