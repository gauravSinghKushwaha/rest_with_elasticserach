log.jsp
http://localhost:9090/driversearch/log.jsp

// Get All
GET http://localhost:9090/driversearch/resources/rest/playlists?startrange=5&endrange=10

// Find with tags
GET http://localhost:9090/driversearch/resources/rest/playlists/tags?q=love+indie

// ADD
POST http://localhost:9090/driversearch/resources/rest/playlists

[{"id":"11","name":"name1","likeCount":4,"playCount":4,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":7},
{"id":"12","name":"name2","likeCount":5,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":8},
{"id":"13","name":"name3","likeCount":3,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":9},
{"id":"14","name":"name4","likeCount":8,"playCount":5,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":10},
{"id":"15","name":"name5","likeCount":10,"playCount":6,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":11},
{"id":"16","name":"name6","likeCount":3,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":12},
{"id":"17","name":"name7","likeCount":1,"playCount":7,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":13},
{"id":"18","name":"name8","likeCount":0,"playCount":9,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":14},
{"id":"19","name":"name9","likeCount":5,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":15},
{"id":"20","name":"name10","likeCount":11,"playCount":5,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":16},
{"id":"1","name":"name1","likeCount":4,"playCount":4,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":7},
{"id":"2","name":"name2","likeCount":5,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":8},
{"id":"3","name":"name3","likeCount":3,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":9},
{"id":"4","name":"name4","likeCount":8,"playCount":5,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":10},
{"id":"5","name":"name5","likeCount":10,"playCount":6,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":11},
{"id":"6","name":"name6","likeCount":3,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":12},
{"id":"7","name":"name7","likeCount":1,"playCount":7,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":13},
{"id":"8","name":"name8","likeCount":0,"playCount":9,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":14},
{"id":"9","name":"name9","likeCount":5,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":15},
{"id":"10","name":"name10","likeCount":11,"playCount":5,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":16}]

// Update
PUT http://localhost:9090/driversearch/resources/rest/playlists

[{"id":"1","name":"namec1","likeCount":4,"playCount":4,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":7},
{"id":"2","name":"namec2","likeCount":5,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":8},
{"id":"3","name":"namec3","likeCount":3,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":9},
{"id":"4","name":"namec4","likeCount":8,"playCount":5,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":10},
{"id":"5","name":"namec5","likeCount":10,"playCount":6,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":11},
{"id":"6","name":"namec6","likeCount":3,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":12},
{"id":"7","name":"namec7","likeCount":1,"playCount":7,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":13},
{"id":"8","name":"namec8","likeCount":0,"playCount":9,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":14},
{"id":"9","name":"namec9","likeCount":5,"playCount":2,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":15},
{"id":"10","name":"namec10","likeCount":11,"playCount":5,"tags":[{"tag":"indie"},{"tag":"love"}],"numberOfSongs":16}]

// explore
GET http://localhost:9090/driversearch/resources/rest/explore/playlist?q=love+indie&startrange=0&endrange=100

