set terminal png         # gnuplot recommends setting terminal before output
set output "graf.png"
set   autoscale                        # scale axes automatically
unset log                              # remove any log-scaling
unset label                            # remove any previous labels
set xtic auto                          # set xtics automatically
set ytic auto                          # set ytics automatically
#set title "Fitness funkcija"
set xlabel "Generacija"
set ylabel "Fitness"
#set key 1,0.2
#set xr [0:15]
#set yr [0:45]
plot	"graf.p" using 1:2 title 'Minimum' with lines, \
		"graf.p" using 1:3 title 'Prosjek' with lines

