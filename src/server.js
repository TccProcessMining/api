const express = require('express');
const routes = require('./routes');

const app = express();
//Uses
app.use(express.json())
app.use(routes)
//Port
app.listen(3333);
