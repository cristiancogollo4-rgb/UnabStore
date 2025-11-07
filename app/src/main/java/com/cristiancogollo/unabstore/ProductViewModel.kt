package com.cristiancogollo.unabstore

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductViewModel : ViewModel() {

    // Inicializa la instancia de Firestore
    private val db = Firebase.firestore

    //  Agregar producto
    fun agregarProducto(producto: Producto, onResult: (Boolean, String) -> Unit) {
        db.collection("Producto")
            .add(producto)
            .addOnSuccessListener {
                onResult(true, "Producto agregado correctamente")
            }
            .addOnFailureListener { e: Exception ->
                onResult(false, "Error al agregar: ${e.message}")
            }
    }

    //  Listar productos
    fun obtenerProductos(onResult: (List<Producto>) -> Unit) {
        db.collection("Producto")
            .get()
            .addOnSuccessListener { result ->
                val productos = result.map { doc ->
                    doc.toObject(Producto::class.java).copy(id = doc.id)
                }
                onResult(productos)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }

    //  Eliminar producto
    fun eliminarProducto(id: String, onResult: (Boolean) -> Unit) {
        db.collection("Producto").document(id)
            .delete()
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    //  Editar producto
    /**
     * Actualiza un producto existente por su ID de Firestore.
     * @param producto El objeto Producto con el ID y los datos actualizados.
     */
    fun editarProducto(producto: Producto, onResult: (Boolean, String) -> Unit) {

        producto.id?.let { docId ->

            val updates = hashMapOf<String, Any>(
                "nombre" to producto.nombre,
                "descripcion" to producto.descripcion,
                "precio" to producto.precio
            )

            db.collection("Producto").document(docId)
                .update(updates)
                .addOnSuccessListener {
                    onResult(true, "Producto actualizado correctamente.")
                }
                .addOnFailureListener { e ->
                    onResult(false, "Error al actualizar: ${e.message}")
                }
        } ?: onResult(false, "Error: ID de producto no encontrado.")
    }
}