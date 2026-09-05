package sv.edu.udb.crudfirebase050926.datos

class Persona {

    var dui: String? = null
    var nombre: String? = null
    var key: String? = null

    constructor() {}

    constructor(dui: String?, nombre: String?) {
        this.dui = dui
        this.nombre = nombre
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "dui" to dui,
            "nombre" to nombre,
            "key" to key
        )
    }
}