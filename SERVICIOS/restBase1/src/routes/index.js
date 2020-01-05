const { Router } = require('express');
const router = Router();

//importamos los metodo creados para tratar las peticiones
const { getUsers, createlogs } = require ('../controlers/index.controler');

router.get('/users', getUsers);
router.post('/users', createlogs);

module.exports = router;    

