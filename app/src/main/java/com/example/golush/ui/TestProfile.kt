package com.example.golush.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.golush.databinding.FragmentTestProfileBinding
import com.example.golush.model.renewate.CovidMain
import com.example.golush.model.renewate.CovidMaterials
import com.example.golush.model.renewate.CovidProducts
import com.example.golush.model.renewate.CovidSubItem
import org.json.JSONArray


private const val REQ_CAPTURE = 100
private const val RES_IMAGE = 100


class TestProfile : Fragment() {

    lateinit var binding: FragmentTestProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestProfileBinding.inflate(layoutInflater, container, false)
        var list = ArrayList<CovidMain>()
        parseJson(list)
        Log.d("covid", list.toString())
        return binding.root
    }

    val jsonData: String = """
        [{
            "room_type": "reception area",
            "room_image": "",
            "products": [{
                    "main_item": "Reception table partition",
                    "sub_items": [{
                            "sub_item_name": "Raksha Reversible Partition (3' width x 2' height)",
                            "image": "",
                            "materials": [{
                                "category": "carpentry",
                                "name": "5 mm acrylic with base",
                                "area": 1,
                                "rate": 2000.0,
                                "unit": "Piece/No.",
                                "price": 2000.0
                            }]
                        },
                        {
                            "sub_item_name": "Raksha Partition c shape (6' wdith x 3' height)",
                            "image": "",
                            "materials": [{
                                    "category": "carpentry",
                                    "name": "Acrylic",
                                    "area": 1,
                                    "rate": 3500.0,
                                    "unit": "Piece/No.",
                                    "price": 3500.0
                                },
                                {
                                    "category": "carpentry",
                                    "name": "Glass",
                                    "area": 1,
                                    "rate": 4000.0,
                                    "unit": "Piece/No.",
                                    "price": 4000.0
                                }
                            ]
                        },
                        {
                            "sub_item_name": "Raksha partition with frame (3' width x 2' height)",
                            "image": "",
                            "materials": [{
                                    "category": "carpentry",
                                    "name": "5 mm glass with Al frame",
                                    "area": 1,
                                    "rate": 3000.0,
                                    "unit": "Piece/No.",
                                    "price": 3000.0
                                },
                                {
                                    "category": "carpentry",
                                    "name": "5 mm acrylic with Al frame",
                                    "area": 1,
                                    "rate": 3250.0,
                                    "unit": "Piece/No.",
                                    "price": 3250.0
                                }
                            ]
                        }
                    ]
                },
                {
                    "main_item": "Waiting Area - Half ",
                    "sub_items": [{
                        "sub_item_name": "Chair side partition (1.5' width x 2.5' height)",
                        "image": "",
                        "materials": [{
                                "category": "carpentry",
                                "name": "Acrylic",
                                "area": 1,
                                "rate": 2000.0,
                                "unit": "Piece/No.",
                                "price": 2000.0
                            },
                            {
                                "category": "carpentry",
                                "name": "Glass",
                                "area": 1,
                                "rate": 2000.0,
                                "unit": "Piece/No.",
                                "price": 2000.0
                            }
                        ]
                    }]
                },
                {
                    "main_item": "Waiting Area - Full",
                    "sub_items": [{
                        "sub_item_name": "Full height movable partition (3' width x 7' height)",
                        "image": "",
                        "materials": [{
                                "category": "carpentry",
                                "name": "Acrylic with frame",
                                "area": 1,
                                "rate": 5500.0,
                                "unit": "Piece/No.",
                                "price": 5500.0
                            },
                            {
                                "category": "carpentry",
                                "name": "Glass with frame",
                                "area": 1,
                                "rate": 6700.0,
                                "unit": "Piece/No.",
                                "price": 6700.0
                            },
                            {
                                "category": "carpentry",
                                "name": "ACP sheet with frame",
                                "area": 1,
                                "rate": 5250.0,
                                "unit": "Piece/No.",
                                "price": 5250.0
                            },
                            {
                                "category": "carpentry",
                                "name": "HPL/Bitumen board with frame",
                                "area": 1,
                                "rate": 4000.0,
                                "unit": "Piece/No.",
                                "price": 4000.0
                            }
                        ]
                    }]
                }
            ]
        },
        {
            "room_type": "workstation design",
            "room_image": "",
            "products": [{
                "main_item": "Work Table",
                "sub_items": [{
                        "sub_item_name": "Countertop partition - L shape (5' width x 2' height)",
                        "image": "",
                        "materials": [{
                            "category": "carpentry",
                            "name": "Acrylic",
                            "area": 1,
                            "rate": 1000.0,
                            "unit": "Piece/No.",
                            "price": 1000.0
                        }]
                    },
                    {
                        "sub_item_name": "Clip on glass partition (3' wdith x 1' height)",
                        "image": "",
                        "materials": [{
                                "category": "carpentry",
                                "name": "Glass",
                                "area": 1,
                                "rate": 800.0,
                                "unit": "Piece/No.",
                                "price": 800.0
                            },
                            {
                                "category": "carpentry",
                                "name": "Acrylic",
                                "area": 1,
                                "rate": 1000.0,
                                "unit": "Piece/No.",
                                "price": 1000.0
                            }
                        ]
                    }
                ]
            }]
        },
        {
            "room_type": "meeting room design",
            "room_image": "",
            "products": [{
                "main_item": "Meeting Table",
                "sub_items": [{
                    "sub_item_name": "Countertop partition - L shape (5' width x 2' height)",
                    "image": "",
                    "materials": [{
                        "category": "carpentry",
                        "name": "Acrylic",
                        "area": 1,
                        "rate": 1000.0,
                        "unit": "Piece/No.",
                        "price": 1000.0
                    }]
                }]
            }]
        },
        {
            "room_type": "cafeteria design",
            "room_image": "",
            "products": [{
                "main_item": "Dining Table",
                "sub_items": [{
                    "sub_item_name": "Countertop partition - L shape (5' width x 2' height)",
                    "image": "",
                    "materials": [{
                        "category": "carpentry",
                        "name": "5 mm Acrylic ",
                        "area": 1,
                        "rate": 1000.0,
                        "unit": "Piece/No.",
                        "price": 1000.0
                    }]
                }]
            }]
        }
        ]
    """.trimIndent()

    fun parseJson(list: ArrayList<CovidMain>) {
        val jsonArray = JSONArray(jsonData)
        var mainList = ArrayList<CovidMain>()
        for (index in 0 until jsonArray.length()) {
            var jsonObject = jsonArray.getJSONObject(index)
            mainList.apply {
                // adding products to a list
                var productsList = ArrayList<CovidProducts>()
                addProducts(jsonObject.getJSONArray("products"), productsList)

                add(
                    CovidMain(
                        jsonObject.getString("room_type"),
                        jsonObject.getString("room_image"),
                        productsList
                    )
                )
            }
        }
        list.addAll(mainList)
    }

    fun addProducts(jsonProducts: JSONArray, productsList: ArrayList<CovidProducts>) {
        for (index in 0 until jsonProducts.length()) {
            var jsonObject = jsonProducts.getJSONObject(index)

            // adding sub items to list
            var subItemsList = ArrayList<CovidSubItem>()
            addSubItems(jsonObject.getJSONArray("sub_items"), subItemsList)

            productsList.add(
                CovidProducts(jsonObject.getString("main_item"), subItemsList)
            )
        }
    }

    fun addSubItems(jsonSubItems: JSONArray, subItemsList: ArrayList<CovidSubItem>) {
        for (index in 0 until jsonSubItems.length()) {
            var jsonObject = jsonSubItems.getJSONObject(index)

            var materialsList = ArrayList<CovidMaterials>()
            addMaterials(jsonObject.getJSONArray("materials"), materialsList)

            subItemsList.add(
                CovidSubItem(
                    jsonObject.getString("sub_item_name"),
                    jsonObject.getString("image"),
                    materialsList
                )
            )
        }
    }

    fun addMaterials(jsonMaterials: JSONArray, materialsList: ArrayList<CovidMaterials>) {
        for (index in 0 until jsonMaterials.length()) {
            var jsonObject = jsonMaterials.getJSONObject(index)
            materialsList.add(
                CovidMaterials(
                    jsonObject.getString("category"),
                    jsonObject.getString("name"),
                    jsonObject.getLong("area"),
                    jsonObject.getLong("rate"),
                    jsonObject.getString("unit"),
                    jsonObject.getLong("price")
                )
            )
        }
    }
}
