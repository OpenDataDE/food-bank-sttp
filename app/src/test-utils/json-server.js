const jsonServer = require('json-server')
const fs = require('fs')
const cors = require('cors')
const bodyParser = require('body-parser')

function server () {
  const uri = process.cwd() + '/src/test-utils/'
  const db = JSON.parse(fs.readFileSync(uri + 'db.json', 'utf8'))
  this.server = jsonServer.create()
  this.defaults = jsonServer.defaults()
  this.routes = jsonServer.router(db)
  // this.server.use(this.defaults)
  // this.server.use(this.routes)
  this.server.use('/data/', this.defaults)
  this.server.use('/data/', this.routes)
  this.server.use(cors())
  this.server.use(bodyParser.json())
  this.server.get('/api/item/category', (req, res) => {
    const items = []
    let ctg = db.product.map(e => e.category).filter(e => {
      if (items.indexOf(e) === -1) {
        items.push(e)
        return true
      }
    })
    res.json({categories: ctg.sort()})
  })
  this.server.get('/api/item/category/:category', (req, res) => {
    let ctg = db.product.filter(e => req.params.category.localeCompare(e.category) === 0)
    res.json({categories: ctg})
  })

  this.server.get('/api/item/', (req, res) => {
    res.json({items: db.product})
  })

  this.server.get('/api/item/:identifier', (req, res) => {
    // referenceNumber
    let item = db.product.filter(e => req.params.identifier.localeCompare(e.referenceNumber) === 0)
    res.json(item)
  })

  this.server.get('/api/order', (req, res) => {
    res.json({items: db.order})
  })

  this.server.put('/api/order', (req, res) => {
    db.order.push(req.body)
    res.sendStatus(200)
  })

  this.server.delete('/api/order', (req, res) => {
    const value = db.order.indexOf(req.body.referenceNumber)
    console.log(value)
    db.order.splice(value, 1)
    res.sendStatus(200)
  })

  this.server.listen(6700, () => {
    console.log('JSON Server is running')
  })
}

server()
