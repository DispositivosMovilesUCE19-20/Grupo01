const express = require('express');
const app = express();

//middlewares
app.use(express.json());
app.use(express.urlencoded({extended: true}));
//rutas
app.use(require('./routes/index'));

const PORT = process.env.PORT || 3000;

//app.listen(3000)  ;
app.listen(PORT, function () {
    console.log('Servidor en el puerto 3000');
  });
