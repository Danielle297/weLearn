'use strict';

/*var STARTING = 0,
    SHOW_QUESTION=20,
    STOP_VOTING=35,
    SHOW_ANSWER=40,
    SHOW_NEXT_QUESTION=50; */
    
var app = require('express')(),
    bodyParser = require('body-parser'),
    http = require('http').Server(app),
    //io = require('socket.io')(http),
    _tests = [], 
    _testSequence = 0,
    _testsIndex={};

// middle-ware
app.use(bodyParser.json());

process.on('uncaughtException', function (exception) {
  console.log(exception); 
});
 
http.listen(3000, function(){
  console.log('listening on *:3000');
});

function responseWithErr(res, data, status) {

    if(!status) {
        status=500;
    }

    res.status(status).json(data);
}


///// CLASSES DEFINITION ////////////

class Question {
    constructor(text, answers, correctAnswer) {
        this.text=text;
        this.abswers=answers;
        this.correctAnswer=correctAnswer;
    }
}


class Test {
    constructor(name, author) {
        this.id = ++_testSequence;
        this.questions=[];
        this.currentQuestion = -1;
        this.subscribers = [];
        this.testRunning=false;
    }
    
        
    addQuestion(q) {
        this.questions.push(q);
    }
    
}
    

/////////////////////////////////////////////////// TEST LOGIC ////////////////////////////////////////////////////

function addTest(test) {
    _tests.push(test);
    _testsIndex[id]=test;
}

addTest(new Test('הסטוריה', 'Adi Barda'));
addTest(new Test('תנך', 'Adi Barda'));
addTest(new Test('מתמתיקה', 'Adi Barda'));
addTest(new Test('הנדסה', 'Adi Barda'));
addTest(new Test('אומנות לחימה', 'Adi Barda'));

////////////// API CALLS /////////////
app.post('/api/subjects', (req,res)=> {
    
    _test.addQuestion(new Question(req.body.text, req.body.answers, req.body.correctAnswer));
    
});

app.post('/api/subjects/:subjectId/questions', (req,res)=> {
    
    _test.addQuestion(new Question(req.body.question, req.body.answers, req.body.correctAnswer));
    
});

app.get('/api/subjects', (req,res)=> {
    res.json( _tests);
});

app.get('/api/subjects/:subjectId/next-question', (req,res)=> {
    
});








