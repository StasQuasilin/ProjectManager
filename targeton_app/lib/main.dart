import 'package:flutter/material.dart';
import 'package:targeton_app/navigation/NavigationMenu.dart';
import 'package:targeton_app/pages/GoalsPage.dart';
import 'package:targeton_app/pages/AppPage.dart';
import 'package:targeton_app/pages/TransactionsPage.dart';
import 'package:targeton_app/pages/TreePage.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key}) : super(key: key);

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  AppPage _page = new GoalPage();

  loadPage(AppPage page){
    setState(() {
      _page = page;
    });
    Navigator.of(context).pop();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(_page.getTitle()),
      ),
      drawer: new NavigationMenu(this),
      body: _page,
    );
  }
}

