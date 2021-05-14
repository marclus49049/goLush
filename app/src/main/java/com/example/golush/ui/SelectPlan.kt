package com.example.golush.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.golush.AppData
import com.example.golush.R
import com.example.golush.databinding.FragmentSelectPlanBinding
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class SelectPlan : Fragment() {

    lateinit var binding: FragmentSelectPlanBinding
    val json = JSONObject(
        """
                {
                  "2":{
                    "2":"500",
                    "3":"600",
                    "4":"700"
                  },
                  "4":{
                    "2":"670",
                    "3":"750",
                    "4":"890"
                  }
                }
            """.trimIndent()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectPlanBinding.inflate(layoutInflater, container, false)

        binding.planRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            radioButtonPlan(group, checkedId)
        })
        binding.groupNumberPeople.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            radioButtonPlan(group, checkedId)
        })
        binding.groupRecipesWeek.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            radioButtonPlan(group, checkedId)
        })

        Handler().postDelayed(
            {
                binding.planNonVeg.isChecked = true
                binding.people2.isChecked = true
                binding.recipes2.isChecked = true
            },
            200
        )

        binding.nextBtn.setOnClickListener {
            println(AppData.formData)
            Navigation.findNavController(it).navigate(R.id.action_selectPlan_to_userAddress)
        }

        return binding.root
    }

    fun radioButtonPlan(group: RadioGroup, checkId: Int) {

        when (checkId) {
            R.id.plan_non_veg -> {
                AppData.formData.put("plan_type", "non-veg")
                binding.textSubPlanType.text = resources.getText(R.string.meat_amp_veggies)
            }
            R.id.plan_veg -> {
                AppData.formData.put("plan_type", "veg")
                binding.textSubPlanType.text = resources.getText(R.string.veggies)
            }
            R.id.plan_snacks -> {
                AppData.formData.put("plan_type", "snack")
                binding.textSubPlanType.text = resources.getText(R.string.snacks)
            }

            R.id.people_2 -> {
                AppData.formData.put("number_of_people", "2")
            }
            R.id.people_4 -> {
                AppData.formData.put("number_of_people", "4")
            }

            R.id.recipes_2 -> {
                AppData.formData.put("number_of_recepies", "2")
            }
            R.id.recipes_3 -> {
                AppData.formData.put("number_of_recepies", "3")
            }
            R.id.recipes_4 -> {
                AppData.formData.put("number_of_recepies", "4")
            }
        }
        binding.textSubServing.text =
            "${AppData.formData.get("number_of_people")} People / ${AppData.formData.get("number_of_recepies")} Recipes per week"
        Handler().postDelayed(
            {
                binding.amountPerServing.text = "\u20B9${
                    json.getJSONObject(AppData.formData.get("number_of_people"))
                        .getString(AppData.formData.get("number_of_recepies"))
                }"
            },
            200
        )
    }
}
