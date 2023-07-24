package com.example.lostfound.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lostfound.databinding.FragmentMapBinding
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.example.lostfound.R
import com.example.lostfound.utils.PreferenceManager
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener

class MapFragment : Fragment() {
    private var _binding : FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapView: MapView
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var annotationPlugin: AnnotationPlugin
    private lateinit var pointAnnotationManager: PointAnnotationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        mapView = binding.mapView
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            annotationPlugin = mapView.annotations //Get the annotation API from the MapView
            pointAnnotationManager = annotationPlugin.createPointAnnotationManager() //Create a PointAnnotationManager to manage point annotations on the map
            addAnnotationToMap()
        }
        preferenceManager = PreferenceManager.getInstance(requireContext())
        return binding.root
    }

    /**
     * Adds a point annotation with a custom icon to the map and enables updating its location on map click.
     *
     * If the initial location is not set, the map listens for a click event. Clicking the map updates the
     * annotation's location to the clicked point's coordinates.
     *
     * @throws IllegalArgumentException if the provided context or drawable resource ID is invalid.
     */
    private fun addAnnotationToMap(){
        try {
            bitmapFromDrawableRes(
                requireContext(),
                R.drawable.map_pin
            )?.let { icon ->
                var point = preferenceManager.getLocationPoint()
                if(point.longitude() == 0.0 && point.latitude() == 0.0){
                    Toast.makeText(requireContext(), "Point is null", Toast.LENGTH_SHORT).show()
                    mapView.getMapboxMap().addOnMapClickListener { p ->
                        point = p
                        val pointAnnotationOptions =
                            PointAnnotationOptions() //Create a PointAnnotationOptions to define the properties of the point annotation
                                .withPoint(point)
                                .withIconImage(icon)

                        //to prevent adding more markers, just updating one marker
                        if(pointAnnotationManager.annotations.isEmpty())
                            pointAnnotationManager.create(pointAnnotationOptions)
                        else{
                            val annotation = pointAnnotationManager.annotations[0]
                            annotation.point = Point.fromLngLat(p.longitude(), p.latitude())
                            pointAnnotationManager.update(annotation)
                        }

                        if (preferenceManager.saveLocation(point)){
                            Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
                        }
                        true
                    }
                }
                else{
                    val pointAnnotationOptions =
                        PointAnnotationOptions() //Create a PointAnnotationOptions to define the properties of the point annotation
                            .withPoint(point)
                            .withIconImage(icon)
                    pointAnnotationManager.create(pointAnnotationOptions)

                    mapView.getMapboxMap().addOnMapClickListener { p ->
                        point = p
                        val annotation = pointAnnotationManager.annotations[0]
                        annotation.point = point
                        pointAnnotationManager.update(annotation)

                        if (preferenceManager.saveLocation(point)){
                            Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
                        }

                        true
                    }
                }
//                mapView.getMapboxMap().setCamera(cameraPosition) // Use easeCamera to smoothly animate the camera change
            }
        }catch (e: java.lang.IllegalArgumentException){
            Log.e("AddAnnotationToMap", "IllegalArgumentException occurred: ${e.message}")
        }
    }

    /**
     * Converts a drawable resource specified by its resource ID to a Bitmap.
     *
     * @param context The context used to access the application's resources.
     * @param resourceId The resource ID of the drawable to be converted to a Bitmap.
     * @return A Bitmap representation of the drawable, or null if the input drawable is null.
     */
    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    /**
     * Converts a Drawable to a Bitmap.
     *
     * @param sourceDrawable The input Drawable to be converted to a Bitmap.
     * @return A Bitmap representation of the input Drawable, or null if the input is null.
     */
    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap?{
        if(sourceDrawable == null)
            return null

        // If the sourceDrawable is already a BitmapDrawable, simply return the underlying bitmap
        return if (sourceDrawable is BitmapDrawable)
            sourceDrawable.bitmap
        else{
            // If the sourceDrawable is not a BitmapDrawable, create a new Drawable from its constant state
            // and make it mutable so that it can be modified
            val constantState = sourceDrawable.constantState ?: return null
            //making a copy of sourceDrawable into drawable
            val drawable = constantState.newDrawable().mutate()

            // Create a new Bitmap with the same dimensions as the Drawable
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )

            // Create a Canvas to draw the Drawable onto the Bitmap
            val canvas = Canvas(bitmap)

            // Set the bounds of the Drawable to match the dimensions of the Canvas
            drawable.setBounds(0, 0, canvas.width, canvas.height)

            // Draw the Drawable onto the Canvas, which will be reflected in the Bitmap
            drawable.draw(canvas)

            // Return the resulting Bitmap
            bitmap
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}