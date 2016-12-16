log.jsp
http://localhost:9090/8tracks/log.jsp

// Get All
GET http://localhost:9090/8tracks/resources/rest/playlists?startrange=0&endrange=100

// Find with tags
GET http://localhost:9090/8tracks/resources/rest/playlists/tags?q=love+indie

// ADD
POST http://localhost:9090/8tracks/resources/rest/playlists
[{"id":"1","name":"name1","likeCount":4,"playCount":4,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":7},
{"id":"2","name":"name2","likeCount":5,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":8},
{"id":"3","name":"name3","likeCount":3,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":9},
{"id":"4","name":"name4","likeCount":8,"playCount":5,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":10},
{"id":"5","name":"name5","likeCount":10,"playCount":6,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":11},
{"id":"6","name":"name6","likeCount":3,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":12},
{"id":"7","name":"name7","likeCount":1,"playCount":7,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":13},
{"id":"8","name":"name8","likeCount":0,"playCount":9,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":14},
{"id":"9","name":"name9","likeCount":5,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":15},
{"id":"10","name":"name10","likeCount":11,"playCount":5,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":16},
{"id":"11","name":"name1","likeCount":4,"playCount":4,"tags":[{"tag":"pop"},{"tag":"hiphop"}],"numberOfSongs":7},
{"id":"12","name":"name2","likeCount":5,"playCount":2,"tags":[{"tag":"brayan"},{"tag":"brayan"}],"numberOfSongs":8},
{"id":"13","name":"name3","likeCount":3,"playCount":2,"tags":[{"tag":"hindi"},{"tag":"pop"}],"numberOfSongs":9},
{"id":"14","name":"name4","likeCount":8,"playCount":5,"tags":[{"tag":"jazz"},{"tag":"love"}],"numberOfSongs":10},
{"id":"15","name":"name5","likeCount":10,"playCount":6,"tags":[{"tag":"pop"},{"tag":"love"}],"numberOfSongs":11},
{"id":"16","name":"name6","likeCount":3,"playCount":2,"tags":[{"tag":"madona"},{"tag":"hindi"}],"numberOfSongs":12},
{"id":"17","name":"name7","likeCount":1,"playCount":7,"tags":[{"tag":"indie"},{"tag":"pop"}],"numberOfSongs":13},
{"id":"18","name":"name8","likeCount":0,"playCount":9,"tags":[{"tag":"madona"},{"tag":"brayan"}],"numberOfSongs":14},
{"id":"19","name":"name9","likeCount":5,"playCount":2,"tags":[{"tag":"hindi"},{"tag":"jazz"}],"numberOfSongs":15},
{"id":"20","name":"name10","likeCount":11,"playCount":5,"tags":[{"tag":"pop"},{"tag":"madona"}],"numberOfSongs":16},
{"id":"21","name":"name1","likeCount":4,"playCount":4,"tags":[{"tag":"love"},{"tag":"hiphop"}],"numberOfSongs":7},
{"id":"22","name":"name2","likeCount":5,"playCount":2,"tags":[{"tag":"love"},{"tag":"brayan"}],"numberOfSongs":8},
{"id":"23","name":"name3","likeCount":3,"playCount":2,"tags":[{"tag":"hindi"},{"tag":"pop"}],"numberOfSongs":9},
{"id":"24","name":"name4","likeCount":8,"playCount":5,"tags":[{"tag":"jazz"},{"tag":"love"}],"numberOfSongs":10},
{"id":"25","name":"name5","likeCount":10,"playCount":6,"tags":[{"tag":"pop"},{"tag":"love"}],"numberOfSongs":11},
{"id":"26","name":"name6","likeCount":3,"playCount":2,"tags":[{"tag":"madona"},{"tag":"hindi"}],"numberOfSongs":12},
{"id":"27","name":"name7","likeCount":1,"playCount":7,"tags":[{"tag":"indie"},{"tag":"pop"}],"numberOfSongs":13},
{"id":"28","name":"name8","likeCount":0,"playCount":9,"tags":[{"tag":"madona"},{"tag":"love"}],"numberOfSongs":14},
{"id":"29","name":"name9","likeCount":5,"playCount":2,"tags":[{"tag":"hindi"},{"tag":"jazz"}],"numberOfSongs":15},
{"id":"30","name":"name10","likeCount":11,"playCount":5,"tags":[{"tag":"pop"},{"tag":"madona"}],"numberOfSongs":16}]

// Update
PUT http://localhost:9090/8tracks/resources/rest/playlists

[{"id":"1","name":"name1","likeCount":4,"playCount":4,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":7},
{"id":"2","name":"name2","likeCount":5,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":8},
{"id":"3","name":"name3","likeCount":3,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":9},
{"id":"4","name":"name4","likeCount":8,"playCount":5,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":10},
{"id":"5","name":"name5","likeCount":10,"playCount":6,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":11},
{"id":"6","name":"name6","likeCount":3,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":12},
{"id":"7","name":"name7","likeCount":1,"playCount":7,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":13},
{"id":"8","name":"name8","likeCount":0,"playCount":9,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":14},
{"id":"9","name":"name9","likeCount":5,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":15},
{"id":"10","name":"name10","likeCount":11,"playCount":5,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":16}]

// explore
GET http://localhost:9090/8tracks/resources/rest/explore/playlist?q=love+indie&startrange=0&endrange=100

