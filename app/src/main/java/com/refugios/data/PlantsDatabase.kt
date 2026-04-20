package com.refugios.data

data class MedicinalPlant(
    val id: Int,
    val nombre: String,
    val nombreCientifico: String,
    val uso: String,
    val parteUtilizada: String,
    val preparacion: String,
    val contraindicaciones: String,
    val categoria: String
)

object PlantsDatabase {

    val plants = listOf(
        // Plantas digestivas
        MedicinalPlant(
            id = 1,
            nombre = "Menta",
            nombreCientifico = "Mentha piperita",
            uso = "Digestión, cólicos, náuseas",
            parteUtilizada = "Hojas",
            preparacion = "Infusión: 1 cucharada por taza, 5-10 minutos",
            contraindicaciones = "Embarazo, reflujo, cálculos biliares",
            categoria = "Digestivo"
        ),
        MedicinalPlant(
            id = 2,
            nombre = "Manzanilla",
            nombreCientifico = "Matricaria chamomilla",
            uso = "Digestión, ansiedad, inflamación",
            parteUtilizada = "Flores",
            preparacion = "Infusión: 1 cucharadita por taza, 5 minutos",
            contraindicaciones = "Alergia a margaritas, warfarina",
            categoria = "Digestivo"
        ),
        MedicinalPlant(
            id = 3,
            nombre = "Anís",
            nombreCientifico = "Pimpinella anisum",
            uso = "Gases, cólicos, tos",
            parteUtilizada = "Semillas",
            preparacion = "Infusión: 1 cucharadita por taza, 10 minutos",
            contraindicaciones = "Embarazo, epilepsia",
            categoria = "Digestivo"
        ),
        MedicinalPlant(
            id = 4,
            nombre = "Hierbabuena",
            nombreCientifico = "Mentha spicata",
            uso = "Gases, digestión lenta, dolor de cabeza",
            parteUtilizada = "Hojas",
            preparacion = "Infusión: 2-3 hojas por taza",
            contraindicaciones = "Embarazo",
            categoria = "Digestivo"
        ),
        MedicinalPlant(
            id = 5,
            nombre = "Jengibre",
            nombreCientifico = "Zingiber officinale",
            uso = "Náuseas, vomitos, circulación",
            parteUtilizada = "Raíz",
            preparacion = "Decocción: cortar rodajas, hervir 10 min",
            contraindicaciones = "Embarazo, anticoagulantes, cálculos",
            categoria = "Digestivo"
        ),

        // Plantas antiinflamatorias
        MedicinalPlant(
            id = 6,
            nombre = "Árnica",
            nombreCientifico = "Arnica montana",
            uso = "Golpes, moretones, dolores musculares",
            parteUtilizada = "Flores",
            preparacion = "Compresa: hervir 10 min, aplicar tibia",
            contraindicaciones = "Heridas abiertas, uso interno",
            categoria = "Antiinflamatorio"
        ),
MedicinalPlant(
            id = 7,
            nombre = "Cúrcuma",
            nombreCientifico = "Curcuma longa",
            uso = "Inflamación, dolor articular, antioxidante",
            parteUtilizada = "Raíz",
            preparacion = "Leche dorada: mezclar con leche caliente",
            contraindicaciones = "Embarazo, anticoagulantes",
            categoria = "Antiinflamatorio"
        ),
        MedicinalPlant(
            id = 8,
            nombre = "Boswelia",
            nombreCientifico = "Boswellia serrata",
            uso = "Artritis, inflamación articular",
            parteUtilizada = "Resina",
            preparacion = "Capsula o tintura",
            contraindicaciones = "Embarazo",
            categoria = "Antiinflamatorio"
        ),
        MedicinalPlant(
            id = 9,
            nombre = "Garra del diablo",
            nombreCientifico = "Harpagophytum procumbens",
            uso = "Dolor articular, artritis, espalda",
            parteUtilizada = "Raíz tuberosa",
            preparacion = "Decocción o cápsulas",
            contraindicaciones = "Embarazo, úlcera, cœur",
            categoria = "Antiinflamatorio"
        ),
        MedicinalPlant(
            id = 10,
            nombre = "Romero",
            nombreCientifico = "Rosmarinus officinalis",
            uso = "Dolor muscular, circulación, cabello",
            parteUtilizada = "Hojas",
            preparacion = "Infusión o aceite esencial",
            contraindicaciones = "Embarazo, epilepsia",
            categoria = "Antiinflamatorio"
        ),

        // Plantas para dormir
        MedicinalPlant(
            id = 11,
            nombre = "Valeriana",
            nombreCientifico = "Valeriana officinalis",
            uso = "Insomnio, ansiedad, estrés",
            parteUtilizada = "Raíz",
            preparacion = "Infusión: 15 min antes de dormir",
            contraindicaciones = "Embarazo, niños, conducir",
            categoria = "Sedante"
        ),
        MedicinalPlant(
            id = 12,
            nombre = "Pasiflora",
            nombreCientifico = "Passiflora incarnata",
            uso = "Ansiedad, insomnio, nervios",
            parteUtilizada = "Flores y hojas",
            preparacion = "Infusión: 2 colheres por taza",
            contraindicaciones = "Embarazo, cirugía",
            categoria = "Sedante"
        ),
        MedicinalPlant(
            id = 13,
            nombre = "Tila",
            nombreCientifico = "Tilia europaea",
            uso = "Ansiedad, insomnio, nervios",
            parteUtilizada = "Flores",
            preparacion = "Infusión: 5 minutos",
            contraindicaciones = "Enfermedad cardíaca",
            categoria = "Sedante"
        ),
        MedicinalPlant(
            id = 14,
            nombre = "Melisa",
            nombreCientifico = "Melissa officinalis",
            uso = "Ansiedad, herpes, insomnio",
            parteUtilizada = "Hojas",
            preparacion = "Infusión: 5-10 minutos",
            contraindicaciones = "Hipotiroidismo",
            categoria = "Sedante"
        ),
        MedicinalPlant(
            id = 15,
            nombre = "Amapola",
            nombreCientifico = "Papaver rhoeas",
            uso = "Tos, insomnio, ansiedad",
            parteUtilizada = "Pétalos",
            preparacion = "Infusión",
            contraindicaciones = "Glaucoma",
            categoria = "Sedante"
        ),

        // Plantas respiratorias
        MedicinalPlant(
            id = 16,
            nombre = "Eucalipto",
            nombreCientifico = "Eucalyptus globulus",
            uso = "Resfriado, tos, congestión",
            parteUtilizada = "Hojas",
            preparacion = "Inhalación: hervir y respirar vapores",
            contraindicaciones = "Niños menores de 2 años",
            categoria = "Respiratorio"
        ),
        MedicinalPlant(
            id = 17,
            nombre = "Gordolobo",
            nombreCientifico = "Verbascum thapsus",
            uso = "Tos, bronquitis, garganta",
            parteUtilizada = "Flores",
            preparacion = "Infusión o jarabe",
            contraindicaciones = "Embarazo",
            categoria = "Respiratorio"
        ),
        MedicinalPlant(
            id = 18,
            nombre = "Tomillo",
            nombreCientifico = "Thymus vulgaris",
            uso = "Tos, Infectiones respiratorias",
            parteUtilizada = "Hojas",
            preparacion = "Infusión o jarabe",
            contraindicaciones = "Embarazo",
            categoria = "Respiratorio"
        ),
        MedicinalPlant(
            id = 19,
            nombre = "Hisopo",
            nombreCientifico = "Hyssopus officinalis",
            uso = "Resfriado, tos, asma",
            parteUtilizada = "Hojas",
            preparacion = "Infusión",
            contraindicaciones = "Embarazo, epilepsia",
            categoria = "Respiratorio"
        ),
        MedicinalPlant(
            id = 20,
            nombre = "Llantén",
            nombreCientifico = "Plantago major",
            uso = "Tos, bronquitis, heridas",
            parteUtilizada = "Hojas",
            preparacion = "Infusión o cataplasma",
            contraindicaciones = "Embarazo",
            categoria = "Respiratorio"
        ),

        // Plantas para la piel
        MedicinalPlant(
            id = 21,
            nombre = "Aloe vera",
            nombreCientifico = "Aloe barbadensis",
            uso = "Quemaduras, heridas, psoriasis",
            parteUtilizada = "Gel de la hoja",
            preparacion = "Aplicar gel directamente",
            contraindicaciones = "Alergia",
            categoria = "Piel"
        ),
        MedicinalPlant(
            id = 22,
            nombre = "Caléndula",
            nombreCientifico = "Calendula officinalis",
            uso = "Heridas, dermatitis, quemaduras",
            parteUtilizada = "Flores",
            preparacion = "Crema o infusión",
            contraindicaciones = "Alergia a margaritas",
            categoria = "Piel"
        ),
        MedicinalPlant(
            id = 23,
            nombre = "Hipérico",
            nombreCientifico = "Hypericum perforatum",
            uso = "Heridas, quemaduras, depresión",
            parteUtilizada = "Flores",
            preparacion = "Aceite o infusión",
            contraindicaciones = "Antidepresivos, embarazo",
            categoria = "Piel"
        ),
        MedicinalPlant(
            id = 24,
            nombre = "Centella asiática",
            nombreCientifico = "Centella asiatica",
            uso = "Cicatrización, celulitis, varices",
            parteUtilizada = "Hojas",
            preparacion = "Cápsulas o crema",
            contraindicaciones = "Embarazo, cáncer",
            categoria = "Piel"
        ),
        MedicinalPlant(
            id = 25,
            nombre = "Consuelda",
            nombreCientifico = "Symphytum officinale",
            uso = "Fracturas, heridas, hematomas",
            parteUtilizada = "Raíz",
            preparacion = "Cataplasma o decocción",
            contraindicaciones = "Uso prolongado",
            categoria = "Piel"
        ),

        // Plantas circulatorias
        MedicinalPlant(
            id = 26,
            nombre = "Espino blanco",
            nombreCientifico = "Crataegus monogyna",
            uso = "Corazón, presión, circulación",
            parteUtilizada = "Flores y hojas",
            preparacion = "Infusión o tintura",
            contraindicaciones = "Medicamentos cardíacos",
            categoria = "Circulatorio"
        ),
        MedicinalPlant(
            id = 27,
            nombre = "Castaño de Indias",
            nombreCientifico = "Aesculus hippocastanum",
            uso = "Varices, hemorroides, circulación",
            parteUtilizada = "Semillas",
            preparacion = "Gel o capsulas",
            contraindicaciones = "Enfermedad hepática",
            categoria = "Circulatorio"
        ),
        MedicinalPlant(
            id = 28,
            nombre = "Ginkgo biloba",
            nombreCientifico = "Ginkgo biloba",
            uso = "Memoria, circulación, vértigo",
            parteUtilizada = "Hojas",
            preparacion = "Capsulas o tintura",
            contraindicaciones = "Anticoagulantes, cirugía",
            categoria = "Circulatorio"
        ),
        MedicinalPlant(
            id = 29,
            nombre = "Vid roja",
            nombreCientifico = "Vitis vinifera",
            uso = "Varices, circulación, hemorroides",
            parteUtilizada = "Hojas",
            preparacion = "Infusión o capsulas",
            contraindicaciones = "Ninguna conocida",
            categoria = "Circulatorio"
        ),
        MedicinalPlant(
            id = 30,
            nombre = "Ajo",
            nombreCientifico = "Allium sativum",
            uso = "Colesterol, presión, defensas",
            parteUtilizada = "Dientes",
            preparacion = "Crudo o cocido",
            contraindicaciones = "Anticoagulantes, cirugía",
            categoria = "Circulatorio"
        ),

        // Plantas urinarias
        MedicinalPlant(
            id = 31,
            nombre = "Uva ursi",
            nombreCientifico = "Arctostaphylos uva-ursi",
            uso = "Infecciones urinarias, cistitis",
            parteUtilizada = "Hojas",
            preparacion = "Infusión",
            contraindicaciones = "Embarazo, niños",
            categoria = "Urinario"
        ),
        MedicinalPlant(
            id = 32,
            nombre = "Gayuba",
            nombreCientifico = "Arctostaphylos uva-ursi",
            uso = "Infecciones urinarias",
            parteUtilizada = "Hojas",
            preparacion = "Infusión",
            contraindicaciones = "Embarazo",
            categoria = "Urinario"
        ),
        MedicinalPlant(
            id = 33,
            nombre = "Cola de caballo",
            nombreCientifico = "Equisetum arvense",
            uso = "Huesos, uñas, retención líquidos",
            parteUtilizada = "Tallos",
            preparacion = "Infusión",
            contraindicaciones = "Embarazo, corazón",
            categoria = "Urinario"
        ),
        MedicinalPlant(
            id = 34,
            nombre = "Diente de león",
            nombreCientifico = "Taraxacum officinale",
            uso = "Hígado, digestión, retención",
            parteUtilizada = "Hojas y raíz",
            preparacion = "Infusión o ensalada",
            contraindicaciones = "Obstrucción biliar",
            categoria = "Urinario"
        ),
MedicinalPlant(
            id = 35,
            nombre = "Grama",
            nombreCientifico = "Cynodon dactylon",
            uso = "Cistitis, retención, cálculos",
            parteUtilizada = "Raíz",
            preparacion = "Decocción",
            contraindicaciones = "Ninguna conocida",
            categoria = "Urinario"
        )
    )

    fun searchPlants(query: String): List<MedicinalPlant> {
        if (query.isBlank()) return plants
        val lowerQuery = query.lowercase()
        return plants.filter {
            it.nombre.lowercase().contains(lowerQuery) ||
            it.nombreCientifico.lowercase().contains(lowerQuery) ||
            it.uso.lowercase().contains(lowerQuery) ||
            it.categoria.lowercase().contains(lowerQuery)
        }
    }

    fun getByCategory(category: String): List<MedicinalPlant> {
        return plants.filter { it.categoria.equals(category, ignoreCase = true) }
    }

    fun getCategories(): List<String> {
        return plants.map { it.categoria }.distinct().sorted()
    }
}