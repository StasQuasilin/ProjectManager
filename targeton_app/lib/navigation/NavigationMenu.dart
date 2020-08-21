import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:targeton_app/pages/GoalsPage.dart';
import 'package:targeton_app/pages/TransactionsPage.dart';
import 'package:targeton_app/pages/TreePage.dart';

class NavigationMenu extends StatelessWidget{

  final _myHomePageState;
  NavigationMenu(this._myHomePageState);

  final _goalPage = new GoalPage();
  final _treePage = new TreePage();


  @override
  Widget build(BuildContext context) {

    final _transactions = new TransactionsPage(_myHomePageState);

    return new Drawer(
        child: new ListView(
            children: <Widget>[
              new DrawerHeader(child: new Text("Menu")),
              new ListTile(
                  title: new Text("Goals"),
                  onTap: () {
                    _myHomePageState.loadPage(_goalPage);
                  }
              ),
              new ListTile(
                title: new Text("Tree"),
                onTap: () {
                  _myHomePageState.loadPage(_treePage);
                },
              ),
              new ListTile(
                title: new Text("Transactions"),
                onTap: (){
                  _myHomePageState.loadPage(_transactions);
                },
              )
            ]
        )
    );
  }
}