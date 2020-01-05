const { Pool } = require('pg');

var pg = require('pg');
pg.defaults.ssl = true;

//configuracion para la base de datos
const configuracion = {
    host : 'ec2-54-235-250-38.compute-1.amazonaws.com',
    user: 'matlqspppekfft',    
    password : 'a20944b0cd28dd7e8386899907d26c222302821eb1f7753fc4ba84e9ca6f5073',
    database : 'dbhg2vpi6mpq5l',
    port : '5432',
    ssl:{

    }
};

//pool de conexion
const pool = new Pool (configuracion);

//metodo para tratar el get
const getUsers = async (req, res) => {
    const response = await pool.query('select * from logs');
    res.status(200).json(response.rows);
};

//metodo para el post
const createlogs = async (req, res) =>{
    const { usuario, fechaentrada, fechasalida, nombre, modelo, tversion } = req.body;
    
    const response = await pool.query('INSERT INTO logs (usuario, fechaentrada, fechasalida, nombre, modelo, tversion) VALUES ($1, $2, $3, $4, $5, $6)', [usuario,fechaentrada,fechasalida,nombre,modelo,tversion]);
    console.log(response);
    res.send(req.body);
};

//exportamos los modulos creados en ese archivo
module.exports = {
    getUsers,
    createlogs
}