package com.cristiancogollo.unabstore

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductViewModel : ViewModel() {

    // Inicializa la instancia de Firestore
    private val db = Firebase.firestore

    //  Agregar producto
    /**
     * Guarda un nuevo producto en Firestore en la colección "Producto".
     * @param producto El objeto Producto a guardar.
     * @param onResult Callback que retorna éxito (Boolean) y un mensaje (String).
     */
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
    /**
     * Obtiene todos los productos de Firestore.
     * @param onResult Callback que retorna la lista de productos (incluyendo el ID de Firestore).
     */
    fun obtenerProductos(onResult: (List<Producto>) -> Unit) {
        db.collection("Producto")
            .get()
            .addOnSuccessListener { result ->
                val productos = result.map { doc ->
                    // Mapea el documento a Producto, copiando el ID de Firestore para eliminar/editar
                    doc.toObject(Producto::class.java).copy(id = doc.id)
                }
                onResult(productos)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }

    //  Eliminar producto
    /**
     * Elimina un producto por su ID de Firestore.
     * @param id El ID del documento a eliminar.
     * @param onResult Callback que retorna éxito (Boolean).
     */
    fun eliminarProducto(id: String, onResult: (Boolean) -> Unit) {
        db.collection("Producto").document(id)
            .delete()
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}