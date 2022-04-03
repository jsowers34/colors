(ns colors.core
    (:gen-class))

(require 'clojure.walk)

(def test-color-map
  "A color map used to test new code.  The actual map is much to large to be useful in checking new functions."
     [{:name "red" :red 255 :green 0 :blue 0}
     {:name "green" :red 0 :green 255 :blue 0}
     {:name "blue" :red 0 :green 0 :blue 255}
     ])

(def unix-color-map
  "The classic Unix RGB color map.  Modified so that all names are lowercase and have no spaces.
   The original was very redundant in that  'White Smoke' was identical to 'white smoke', 'whitesmoke',
   and 'WhiteSmoke'.  Allowing the code to change the entry to lower case and remove spaces allows this
   entry to be reduced from 4 to 1."
     [{:name "aliceblue" :red 240 :green 248 :blue 255}
     {:name "antiquewhite" :red 250 :green 235 :blue 215}
     {:name "antiquewhite1" :red 255 :green 239 :blue 219}
     {:name "antiquewhite2" :red 238 :green 223 :blue 204}
     {:name "antiquewhite3" :red 205 :green 192 :blue 176}
     {:name "antiquewhite4" :red 139 :green 131 :blue 120}
     {:name "aquamarine" :red 50 :green 191 :blue 193}
     {:name "aquamarine1" :red 127 :green 255 :blue 212}
     {:name "aquamarine2" :red 118 :green 238 :blue 198}
     {:name "aquamarine3" :red 102 :green 205 :blue 170}
     {:name "aquamarine4" :red 69 :green 139 :blue 116}
     {:name "azure" :red 240 :green 255 :blue 255}
     {:name "azure1" :red 240 :green 255 :blue 255}
     {:name "azure2" :red 224 :green 238 :blue 238}
     {:name "azure3" :red 193 :green 205 :blue 205}
     {:name "azure4" :red 131 :green 139 :blue 139}
     {:name "beige" :red 245 :green 245 :blue 220}
     {:name "bisque" :red 255 :green 228 :blue 196}
     {:name "bisque1" :red 255 :green 228 :blue 196}
     {:name "bisque2" :red 238 :green 213 :blue 183}
     {:name "bisque3" :red 205 :green 183 :blue 158}
     {:name "bisque4" :red 139 :green 125 :blue 107}
     {:name "black" :red 0 :green 0 :blue 0}
     {:name "blanchedalmond" :red 255 :green 235 :blue 205}
     {:name "blue" :red 0 :green 0 :blue 255}
     {:name "blue1" :red 0 :green 0 :blue 255}
     {:name "blue2" :red 0 :green 0 :blue 238}
     {:name "blue3" :red 0 :green 0 :blue 205}
     {:name "blue4" :red 0 :green 0 :blue 139}
     {:name "blueviolet" :red 138 :green 43 :blue 226}
     {:name "brickred" :red 128 :green 0 :blue 0}
     {:name "brown" :red 165 :green 42 :blue 42}
     {:name "brown1" :red 255 :green 64 :blue 64}
     {:name "brown2" :red 238 :green 59 :blue 59}
     {:name "brown3" :red 205 :green 51 :blue 51}
     {:name "brown4" :red 139 :green 35 :blue 35}
     {:name "burlywood" :red 222 :green 184 :blue 135}
     {:name "burlywood1" :red 255 :green 211 :blue 155}
     {:name "burlywood2" :red 238 :green 197 :blue 145}
     {:name "burlywood3" :red 205 :green 170 :blue 125}
     {:name "burlywood4" :red 139 :green 115 :blue 85}
     {:name "cadetblue" :red 95 :green 146 :blue 158}
     {:name "cadetblue1" :red 152 :green 245 :blue 255}
     {:name "cadetblue2" :red 142 :green 229 :blue 238}
     {:name "cadetblue3" :red 122 :green 197 :blue 205}
     {:name "cadetblue4" :red 83 :green 134 :blue 139}
     {:name "chartreuse" :red 127 :green 255 :blue 0}
     {:name "chartreuse1" :red 127 :green 255 :blue 0}
     {:name "chartreuse2" :red 118 :green 238 :blue 0}
     {:name "chartreuse3" :red 102 :green 205 :blue 0}
     {:name "chartreuse4" :red 69 :green 139 :blue 0}
     {:name "chocolate" :red 210 :green 105 :blue 30}
     {:name "chocolate1" :red 255 :green 127 :blue 36}
     {:name "chocolate2" :red 238 :green 118 :blue 33}
     {:name "chocolate3" :red 205 :green 102 :blue 29}
     {:name "chocolate4" :red 139 :green 69 :blue 19}
     {:name "coral" :red 255 :green 114 :blue 86}
     {:name "coral1" :red 255 :green 114 :blue 86}
     {:name "coral2" :red 238 :green 106 :blue 80}
     {:name "coral3" :red 205 :green 91 :blue 69}
     {:name "coral4" :red 139 :green 62 :blue 47}
     {:name "cornflowerblue" :red 34 :green 34 :blue 152}
     {:name "cornsilk" :red 255 :green 248 :blue 220}
     {:name "cornsilk1" :red 255 :green 248 :blue 220}
     {:name "cornsilk2" :red 238 :green 232 :blue 205}
     {:name "cornsilk3" :red 205 :green 200 :blue 177}
     {:name "cornsilk4" :red 139 :green 136 :blue 120}
     {:name "cyan" :red 0 :green 255 :blue 255}
     {:name "cyan1" :red 0 :green 255 :blue 255}
     {:name "cyan2" :red 0 :green 238 :blue 238}
     {:name "cyan3" :red 0 :green 205 :blue 205}
     {:name "cyan4" :red 0 :green 139 :blue 139}
     {:name "darkgoldenrod" :red 184 :green 134 :blue 11}
     {:name "darkgoldenrod1" :red 255 :green 185 :blue 15}
     {:name "darkgoldenrod2" :red 238 :green 173 :blue 14}
     {:name "darkgoldenrod3" :red 205 :green 149 :blue 12}
     {:name "darkgoldenrod4" :red 139 :green 101 :blue 8}
     {:name "darkgreen" :red 0 :green 86 :blue 45}
     {:name "darkkhaki" :red 189 :green 183 :blue 107}
     {:name "darkolivegreen" :red 85 :green 86 :blue 47}
     {:name "darkolivegreen1" :red 202 :green 255 :blue 112}
     {:name "darkolivegreen2" :red 188 :green 238 :blue 104}
     {:name "darkolivegreen3" :red 162 :green 205 :blue 90}
     {:name "darkolivegreen4" :red 110 :green 139 :blue 61}
     {:name "darkorange" :red 255 :green 140 :blue 0}
     {:name "darkorange1" :red 255 :green 127 :blue 0}
     {:name "darkorange2" :red 238 :green 118 :blue 0}
     {:name "darkorange3" :red 205 :green 102 :blue 0}
     {:name "darkorange4" :red 139 :green 69 :blue 0}
     {:name "darkorchid" :red 139 :green 32 :blue 139}
     {:name "darkorchid1" :red 191 :green 62 :blue 255}
     {:name "darkorchid2" :red 178 :green 58 :blue 238}
     {:name "darkorchid3" :red 154 :green 50 :blue 205}
     {:name "darkorchid4" :red 104 :green 34 :blue 139}
     {:name "darksalmon" :red 233 :green 150 :blue 122}
     {:name "darkseagreen" :red 143 :green 188 :blue 143}
     {:name "darkseagreen1" :red 193 :green 255 :blue 193}
     {:name "darkseagreen2" :red 180 :green 238 :blue 180}
     {:name "darkseagreen3" :red 155 :green 205 :blue 155}
     {:name "darkseagreen4" :red 105 :green 139 :blue 105}
     {:name "darkslateblue" :red 56 :green 75 :blue 102}
     {:name "darkslategray" :red 47 :green 79 :blue 79}
     {:name "darkslategray1" :red 151 :green 255 :blue 255}
     {:name "darkslategray2" :red 141 :green 238 :blue 238}
     {:name "darkslategray3" :red 121 :green 205 :blue 205}
     {:name "darkslategray4" :red 82 :green 139 :blue 139}
     {:name "darkslategrey" :red 47 :green 79 :blue 79}
     {:name "darkturquoise" :red 0 :green 166 :blue 166}
     {:name "darkviolet" :red 148 :green 0 :blue 211}
     {:name "deeppink" :red 255 :green 20 :blue 147}
     {:name "deeppink1" :red 255 :green 20 :blue 147}
     {:name "deeppink2" :red 238 :green 18 :blue 137}
     {:name "deeppink3" :red 205 :green 16 :blue 118}
     {:name "deeppink4" :red 139 :green 10 :blue 80}
     {:name "deepskyblue" :red 0 :green 191 :blue 255}
     {:name "deepskyblue1" :red 0 :green 191 :blue 255}
     {:name "deepskyblue2" :red 0 :green 178 :blue 238}
     {:name "deepskyblue3" :red 0 :green 154 :blue 205}
     {:name "deepskyblue4" :red 0 :green 104 :blue 139}
     {:name "dimgray" :red 84 :green 84 :blue 84}
     {:name "dimgrey" :red 84 :green 84 :blue 84}
     {:name "dodgerblue" :red 30 :green 144 :blue 255}
     {:name "dodgerblue1" :red 30 :green 144 :blue 255}
     {:name "dodgerblue2" :red 28 :green 134 :blue 238}
     {:name "dodgerblue3" :red 24 :green 116 :blue 205}
     {:name "dodgerblue4" :red 16 :green 78 :blue 139}
     {:name "firebrick" :red 142 :green 35 :blue 35}
     {:name "firebrick1" :red 255 :green 48 :blue 48}
     {:name "firebrick2" :red 238 :green 44 :blue 44}
     {:name "firebrick3" :red 205 :green 38 :blue 38}
     {:name "firebrick4" :red 139 :green 26 :blue 26}
     {:name "floralwhite" :red 255 :green 250 :blue 240}
     {:name "forestgreen" :red 80 :green 159 :blue 105}
     {:name "gainsboro" :red 220 :green 220 :blue 220}
     {:name "ghostwhite" :red 248 :green 248 :blue 255}
     {:name "gold" :red 218 :green 170 :blue 0}
     {:name "gold1" :red 255 :green 215 :blue 0}
     {:name "gold2" :red 238 :green 201 :blue 0}
     {:name "gold3" :red 205 :green 173 :blue 0}
     {:name "gold4" :red 139 :green 117 :blue 0}
     {:name "goldenrod" :red 239 :green 223 :blue 132}
     {:name "goldenrod1" :red 255 :green 193 :blue 37}
     {:name "goldenrod2" :red 238 :green 180 :blue 34}
     {:name "goldenrod3" :red 205 :green 155 :blue 29}
     {:name "goldenrod4" :red 139 :green 105 :blue 20}
     {:name "gray" :red 126 :green 126 :blue 126}
     {:name "gray0" :red 0 :green 0 :blue 0}
     {:name "gray1" :red 3 :green 3 :blue 3}
     {:name "gray10" :red 26 :green 26 :blue 26}
     {:name "gray100" :red 255 :green 255 :blue 255}
     {:name "gray11" :red 28 :green 28 :blue 28}
     {:name "gray12" :red 31 :green 31 :blue 31}
     {:name "gray13" :red 33 :green 33 :blue 33}
     {:name "gray14" :red 36 :green 36 :blue 36}
     {:name "gray15" :red 38 :green 38 :blue 38}
     {:name "gray16" :red 41 :green 41 :blue 41}
     {:name "gray17" :red 43 :green 43 :blue 43}
     {:name "gray18" :red 46 :green 46 :blue 46}
     {:name "gray19" :red 48 :green 48 :blue 48}
     {:name "gray2" :red 5 :green 5 :blue 5}
     {:name "gray20" :red 51 :green 51 :blue 51}
     {:name "gray21" :red 54 :green 54 :blue 54}
     {:name "gray22" :red 56 :green 56 :blue 56}
     {:name "gray23" :red 59 :green 59 :blue 59}
     {:name "gray24" :red 61 :green 61 :blue 61}
     {:name "gray25" :red 64 :green 64 :blue 64}
     {:name "gray26" :red 66 :green 66 :blue 66}
     {:name "gray27" :red 69 :green 69 :blue 69}
     {:name "gray28" :red 71 :green 71 :blue 71}
     {:name "gray29" :red 74 :green 74 :blue 74}
     {:name "gray3" :red 8 :green 8 :blue 8}
     {:name "gray30" :red 77 :green 77 :blue 77}
     {:name "gray31" :red 79 :green 79 :blue 79}
     {:name "gray32" :red 82 :green 82 :blue 82}
     {:name "gray33" :red 84 :green 84 :blue 84}
     {:name "gray34" :red 87 :green 87 :blue 87}
     {:name "gray35" :red 89 :green 89 :blue 89}
     {:name "gray36" :red 92 :green 92 :blue 92}
     {:name "gray37" :red 94 :green 94 :blue 94}
     {:name "gray38" :red 97 :green 97 :blue 97}
     {:name "gray39" :red 99 :green 99 :blue 99}
     {:name "gray4" :red 10 :green 10 :blue 10}
     {:name "gray40" :red 102 :green 102 :blue 102}
     {:name "gray41" :red 105 :green 105 :blue 105}
     {:name "gray42" :red 107 :green 107 :blue 107}
     {:name "gray43" :red 110 :green 110 :blue 110}
     {:name "gray44" :red 112 :green 112 :blue 112}
     {:name "gray45" :red 115 :green 115 :blue 115}
     {:name "gray46" :red 117 :green 117 :blue 117}
     {:name "gray47" :red 120 :green 120 :blue 120}
     {:name "gray48" :red 122 :green 122 :blue 122}
     {:name "gray49" :red 125 :green 125 :blue 125}
     {:name "gray5" :red 13 :green 13 :blue 13}
     {:name "gray50" :red 127 :green 127 :blue 127}
     {:name "gray51" :red 130 :green 130 :blue 130}
     {:name "gray52" :red 133 :green 133 :blue 133}
     {:name "gray53" :red 135 :green 135 :blue 135}
     {:name "gray54" :red 138 :green 138 :blue 138} 
     {:name "gray55" :red 140 :green 140 :blue 140}
     {:name "gray56" :red 143 :green 143 :blue 143}
     {:name "gray57" :red 145 :green 145 :blue 145}
     {:name "gray58" :red 148 :green 148 :blue 148}
     {:name "gray59" :red 150 :green 150 :blue 150}
     {:name "gray6" :red 15 :green 15 :blue 15}
     {:name "gray60" :red 153 :green 153 :blue 153}
     {:name "gray61" :red 156 :green 156 :blue 156}
     {:name "gray62" :red 158 :green 158 :blue 158}
     {:name "gray63" :red 161 :green 161 :blue 161}
     {:name "gray64" :red 163 :green 163 :blue 163}
     {:name "gray65" :red 166 :green 166 :blue 166}
     {:name "gray66" :red 168 :green 168 :blue 168}
     {:name "gray67" :red 171 :green 171 :blue 171}
     {:name "gray68" :red 173 :green 173 :blue 173}
     {:name "gray69" :red 176 :green 176 :blue 176}
     {:name "gray7" :red 18 :green 18 :blue 18}
     {:name "gray70" :red 179 :green 179 :blue 179}
     {:name "gray71" :red 181 :green 181 :blue 181}
     {:name "gray72" :red 184 :green 184 :blue 184}
     {:name "gray73" :red 186 :green 186 :blue 186}
     {:name "gray74" :red 189 :green 189 :blue 189}
     {:name "gray75" :red 191 :green 191 :blue 191}
     {:name "gray76" :red 194 :green 194 :blue 194}
     {:name "gray77" :red 196 :green 196 :blue 196}
     {:name "gray78" :red 199 :green 199 :blue 199}
     {:name "gray79" :red 201 :green 201 :blue 201}
     {:name "gray8" :red 20 :green 20 :blue 20}
     {:name "gray80" :red 204 :green 204 :blue 204}
     {:name "gray81" :red 207 :green 207 :blue 207}
     {:name "gray82" :red 209 :green 209 :blue 209}
     {:name "gray83" :red 212 :green 212 :blue 212}
     {:name "gray84" :red 214 :green 214 :blue 214}
     {:name "gray85" :red 217 :green 217 :blue 217}
     {:name "gray86" :red 219 :green 219 :blue 219}
     {:name "gray87" :red 222 :green 222 :blue 222}
     {:name "gray88" :red 224 :green 224 :blue 224}
     {:name "gray89" :red 227 :green 227 :blue 227}
     {:name "gray9" :red 23 :green 23 :blue 23}
     {:name "gray90" :red 229 :green 229 :blue 229}
     {:name "gray91" :red 232 :green 232 :blue 232}
     {:name "gray92" :red 235 :green 235 :blue 235}
     {:name "gray93" :red 237 :green 237 :blue 237}
     {:name "gray94" :red 240 :green 240 :blue 240}
     {:name "gray95" :red 242 :green 242 :blue 242}
     {:name "gray96" :red 245 :green 245 :blue 245}
     {:name "gray97" :red 247 :green 247 :blue 247}
     {:name "gray98" :red 250 :green 250 :blue 250}
     {:name "gray99" :red 252 :green 252 :blue 252}
     {:name "green" :red 0 :green 255 :blue 0}
     {:name "green1" :red 0 :green 255 :blue 0}
     {:name "green2" :red 0 :green 238 :blue 0}
     {:name "green3" :red 0 :green 205 :blue 0}
     {:name "green4" :red 0 :green 139 :blue 0}
     {:name "greenyellow" :red 173 :green 255 :blue 47}
     {:name "grey" :red 126 :green 126 :blue 126}
     {:name "grey0" :red 0 :green 0 :blue 0}
     {:name "grey1" :red 3 :green 3 :blue 3}
     {:name "grey10" :red 26 :green 26 :blue 26}
     {:name "grey100" :red 255 :green 255 :blue 255}
     {:name "grey11" :red 28 :green 28 :blue 28}
     {:name "grey12" :red 31 :green 31 :blue 31}
     {:name "grey13" :red 33 :green 33 :blue 33}
     {:name "grey14" :red 36 :green 36 :blue 36}
     {:name "grey15" :red 38 :green 38 :blue 38}
     {:name "grey16" :red 41 :green 41 :blue 41}
     {:name "grey17" :red 43 :green 43 :blue 43}
     {:name "grey18" :red 46 :green 46 :blue 46}
     {:name "grey19" :red 48 :green 48 :blue 48}
     {:name "grey2" :red 5 :green 5 :blue 5}
     {:name "grey20" :red 51 :green 51 :blue 51}
     {:name "grey21" :red 54 :green 54 :blue 54}
     {:name "grey22" :red 56 :green 56 :blue 56}
     {:name "grey23" :red 59 :green 59 :blue 59}
     {:name "grey24" :red 61 :green 61 :blue 61}
     {:name "grey25" :red 64 :green 64 :blue 64}
     {:name "grey26" :red 66 :green 66 :blue 66}
     {:name "grey27" :red 69 :green 69 :blue 69}
     {:name "grey28" :red 71 :green 71 :blue 71}
     {:name "grey29" :red 74 :green 74 :blue 74}
     {:name "grey3" :red 8 :green 8 :blue 8}
     {:name "grey30" :red 77 :green 77 :blue 77}
     {:name "grey31" :red 79 :green 79 :blue 79}
     {:name "grey32" :red 82 :green 82 :blue 82}
     {:name "grey33" :red 84 :green 84 :blue 84}
     {:name "grey34" :red 87 :green 87 :blue 87}
     {:name "grey35" :red 89 :green 89 :blue 89}
     {:name "grey36" :red 92 :green 92 :blue 92}
     {:name "grey37" :red 94 :green 94 :blue 94}
     {:name "grey38" :red 97 :green 97 :blue 97}
     {:name "grey39" :red 99 :green 99 :blue 99}
     {:name "grey4" :red 10 :green 10 :blue 10}
     {:name "grey40" :red 102 :green 102 :blue 102}
     {:name "grey41" :red 105 :green 105 :blue 105}
     {:name "grey42" :red 107 :green 107 :blue 107}
     {:name "grey43" :red 110 :green 110 :blue 110}
     {:name "grey44" :red 112 :green 112 :blue 112}
     {:name "grey45" :red 115 :green 115 :blue 115}
     {:name "grey46" :red 117 :green 117 :blue 117}
     {:name "grey47" :red 120 :green 120 :blue 120}
     {:name "grey48" :red 122 :green 122 :blue 122}
     {:name "grey49" :red 125 :green 125 :blue 125}
     {:name "grey5" :red 13 :green 13 :blue 13}
     {:name "grey50" :red 127 :green 127 :blue 127}
     {:name "grey51" :red 130 :green 130 :blue 130}
     {:name "grey52" :red 133 :green 133 :blue 133}
     {:name "grey53" :red 135 :green 135 :blue 135}
     {:name "grey54" :red 138 :green 138 :blue 138}
     {:name "grey55" :red 140 :green 140 :blue 140}
     {:name "grey56" :red 143 :green 143 :blue 143}
     {:name "grey57" :red 145 :green 145 :blue 145}
     {:name "grey58" :red 148 :green 148 :blue 148}
     {:name "grey59" :red 150 :green 150 :blue 150}
     {:name "grey6" :red 15 :green 15 :blue 15}
     {:name "grey60" :red 153 :green 153 :blue 153}
     {:name "grey61" :red 156 :green 156 :blue 156}
     {:name "grey62" :red 158 :green 158 :blue 158}
     {:name "grey63" :red 161 :green 161 :blue 161}
     {:name "grey64" :red 163 :green 163 :blue 163}
     {:name "grey65" :red 166 :green 166 :blue 166}
     {:name "grey66" :red 168 :green 168 :blue 168}
     {:name "grey67" :red 171 :green 171 :blue 171}
     {:name "grey68" :red 173 :green 173 :blue 173}
     {:name "grey69" :red 176 :green 176 :blue 176}
     {:name "grey7" :red 18 :green 18 :blue 18}
     {:name "grey70" :red 179 :green 179 :blue 179}
     {:name "grey71" :red 181 :green 181 :blue 181}
     {:name "grey72" :red 184 :green 184 :blue 184}
     {:name "grey73" :red 186 :green 186 :blue 186}
     {:name "grey74" :red 189 :green 189 :blue 189}
     {:name "grey75" :red 191 :green 191 :blue 191}
     {:name "grey76" :red 194 :green 194 :blue 194}
     {:name "grey77" :red 196 :green 196 :blue 196}
     {:name "grey78" :red 199 :green 199 :blue 199}
     {:name "grey79" :red 201 :green 201 :blue 201}
     {:name "grey8" :red 20 :green 20 :blue 20}
     {:name "grey80" :red 204 :green 204 :blue 204}
     {:name "grey81" :red 207 :green 207 :blue 207}
     {:name "grey82" :red 209 :green 209 :blue 209}
     {:name "grey83" :red 212 :green 212 :blue 212}
     {:name "grey84" :red 214 :green 214 :blue 214}
     {:name "grey85" :red 217 :green 217 :blue 217}
     {:name "grey86" :red 219 :green 219 :blue 219}
     {:name "grey87" :red 222 :green 222 :blue 222}
     {:name "grey88" :red 224 :green 224 :blue 224}
     {:name "grey89" :red 227 :green 227 :blue 227}
     {:name "grey9" :red 23 :green 23 :blue 23}
     {:name "grey90" :red 229 :green 229 :blue 229}
     {:name "grey91" :red 232 :green 232 :blue 232}
     {:name "grey92" :red 235 :green 235 :blue 235}
     {:name "grey93" :red 237 :green 237 :blue 237}
     {:name "grey94" :red 240 :green 240 :blue 240}
     {:name "grey95" :red 242 :green 242 :blue 242}
     {:name "grey96" :red 245 :green 245 :blue 245}
     {:name "grey97" :red 247 :green 247 :blue 247}
     {:name "grey98" :red 250 :green 250 :blue 250}
     {:name "grey99" :red 252 :green 252 :blue 252}
     {:name "honeydew" :red 240 :green 255 :blue 240}
     {:name "honeydew1" :red 240 :green 255 :blue 240}
     {:name "honeydew2" :red 224 :green 238 :blue 224}
     {:name "honeydew3" :red 193 :green 205 :blue 193}
     {:name "honeydew4" :red 131 :green 139 :blue 131}
     {:name "hotpink" :red 255 :green 105 :blue 180}
     {:name "hotpink1" :red 255 :green 110 :blue 180}
     {:name "hotpink2" :red 238 :green 106 :blue 167}
     {:name "hotpink3" :red 205 :green 96 :blue 144}
     {:name "hotpink4" :red 139 :green 58 :blue 98}
     {:name "indianred" :red 107 :green 57 :blue 57}
     {:name "indianred1" :red 255 :green 106 :blue 106}
     {:name "indianred2" :red 238 :green 99 :blue 99}
     {:name "indianred3" :red 205 :green 85 :blue 85}
     {:name "indianred4" :red 139 :green 58 :blue 58}
     {:name "ivory" :red 255 :green 255 :blue 240}
     {:name "ivory1" :red 255 :green 255 :blue 240}
     {:name "ivory2" :red 238 :green 238 :blue 224}
     {:name "ivory3" :red 205 :green 205 :blue 193}
     {:name "ivory4" :red 139 :green 139 :blue 131}
     {:name "khaki" :red 179 :green 179 :blue 126}
     {:name "khaki1" :red 255 :green 246 :blue 143}
     {:name "khaki2" :red 238 :green 230 :blue 133}
     {:name "khaki3" :red 205 :green 198 :blue 115}
     {:name "khaki4" :red 139 :green 134 :blue 78}
     {:name "lavender" :red 230 :green 230 :blue 250}
     {:name "lavenderblush" :red 255 :green 240 :blue 245}
     {:name "lavenderblush1" :red 255 :green 240 :blue 245}
     {:name "lavenderblush2" :red 238 :green 224 :blue 229}
     {:name "lavenderblush3" :red 205 :green 193 :blue 197}
     {:name "lavenderblush4" :red 139 :green 131 :blue 134}
     {:name "lawngreen" :red 124 :green 252 :blue 0}
     {:name "lemonchiffon" :red 255 :green 250 :blue 205}
     {:name "lemonchiffon1" :red 255 :green 250 :blue 205}
     {:name "lemonchiffon2" :red 238 :green 233 :blue 191}
     {:name "lemonchiffon3" :red 205 :green 201 :blue 165}
     {:name "lemonchiffon4" :red 139 :green 137 :blue 112}
     {:name "lightblue" :red 176 :green 226 :blue 255}
     {:name "lightblue1" :red 191 :green 239 :blue 255}
     {:name "lightblue2" :red 178 :green 223 :blue 238}
     {:name "lightblue3" :red 154 :green 192 :blue 205}
     {:name "lightblue4" :red 104 :green 131 :blue 139}
     {:name "lightcoral" :red 240 :green 128 :blue 128}
     {:name "lightcyan" :red 224 :green 255 :blue 255}
     {:name "lightcyan1" :red 224 :green 255 :blue 255}
     {:name "lightcyan2" :red 209 :green 238 :blue 238}
     {:name "lightcyan3" :red 180 :green 205 :blue 205}
     {:name "lightcyan4" :red 122 :green 139 :blue 139}
     {:name "lightgoldenrod" :red 238 :green 221 :blue 130}
     {:name "lightgoldenrod1" :red 255 :green 236 :blue 139}
     {:name "lightgoldenrod2" :red 238 :green 220 :blue 130}
     {:name "lightgoldenrod3" :red 205 :green 190 :blue 112}
     {:name "lightgoldenrod4" :red 139 :green 129 :blue 76}
     {:name "lightgoldenrodyellow" :red 250 :green 250 :blue 210}
     {:name "lightgray" :red 168 :green 168 :blue 168}
     {:name "lightgrey" :red 168 :green 168 :blue 168}
     {:name "lightpink" :red 255 :green 182 :blue 193}
     {:name "lightpink1" :red 255 :green 174 :blue 185}
     {:name "lightpink2" :red 238 :green 162 :blue 173}
     {:name "lightpink3" :red 205 :green 140 :blue 149}
     {:name "lightpink4" :red 139 :green 95 :blue 101}
     {:name "lightsalmon" :red 255 :green 160 :blue 122}
     {:name "lightsalmon1" :red 255 :green 160 :blue 122}
     {:name "lightsalmon2" :red 238 :green 149 :blue 114}
     {:name "lightsalmon3" :red 205 :green 129 :blue 98}
     {:name "lightsalmon4" :red 139 :green 87 :blue 66}
     {:name "lightseagreen" :red 32 :green 178 :blue 170}
     {:name "lightskyblue" :red 135 :green 206 :blue 250}
     {:name "lightskyblue1" :red 176 :green 226 :blue 255}
     {:name "lightskyblue2" :red 164 :green 211 :blue 238}
     {:name "lightskyblue3" :red 141 :green 182 :blue 205}
     {:name "lightskyblue4" :red 96 :green 123 :blue 139}
     {:name "lightslateblue" :red 132 :green 112 :blue 255}
     {:name "lightslategray" :red 119 :green 136 :blue 153}
     {:name "lightslategrey" :red 119 :green 136 :blue 153}
     {:name "lightsteelblue" :red 124 :green 152 :blue 211}
     {:name "lightsteelblue1" :red 202 :green 225 :blue 255}
     {:name "lightsteelblue2" :red 188 :green 210 :blue 238}
     {:name "lightsteelblue3" :red 162 :green 181 :blue 205}
     {:name "lightsteelblue4" :red 110 :green 123 :blue 139}
     {:name "lightyellow" :red 255 :green 255 :blue 224}
     {:name "lightyellow1" :red 255 :green 255 :blue 224}
     {:name "lightyellow2" :red 238 :green 238 :blue 209}
     {:name "lightyellow3" :red 205 :green 205 :blue 180}
     {:name "lightyellow4" :red 139 :green 139 :blue 122}
     {:name "limegreen" :red 0 :green 175 :blue 20}
     {:name "linen" :red 250 :green 240 :blue 230}
     {:name "magenta" :red 255 :green 0 :blue 255}
     {:name "magenta1" :red 255 :green 0 :blue 255}
     {:name "magenta2" :red 238 :green 0 :blue 238}
     {:name "magenta3" :red 205 :green 0 :blue 205}
     {:name "magenta4" :red 139 :green 0 :blue 139}
     {:name "maroon" :red 143 :green 0 :blue 82}
     {:name "maroon1" :red 255 :green 52 :blue 179}
     {:name "maroon2" :red 238 :green 48 :blue 167}
     {:name "maroon3" :red 205 :green 41 :blue 144}
     {:name "maroon4" :red 139 :green 28 :blue 98}
     {:name "mediumaquamarine" :red 0 :green 147 :blue 143}
     {:name "mediumblue" :red 50 :green 50 :blue 204}
     {:name "mediumforestgreen" :red 50 :green 129 :blue 75}
     {:name "mediumgoldenrod" :red 209 :green 193 :blue 102}
     {:name "mediumorchid" :red 189 :green 82 :blue 189}
     {:name "mediumorchid1" :red 224 :green 102 :blue 255}
     {:name "mediumorchid2" :red 209 :green 95 :blue 238}
     {:name "mediumorchid3" :red 180 :green 82 :blue 205}
     {:name "mediumorchid4" :red 122 :green 55 :blue 139}
     {:name "mediumpurple" :red 147 :green 112 :blue 219}
     {:name "mediumpurple1" :red 171 :green 130 :blue 255}
     {:name "mediumpurple2" :red 159 :green 121 :blue 238}
     {:name "mediumpurple3" :red 137 :green 104 :blue 205}
     {:name "mediumpurple4" :red 93 :green 71 :blue 139}
     {:name "mediumseagreen" :red 52 :green 119 :blue 102}
     {:name "mediumslateblue" :red 106 :green 106 :blue 141}
     {:name "mediumspringgreen" :red 35 :green 142 :blue 35}
     {:name "mediumturquoise" :red 0 :green 210 :blue 210}
     {:name "mediumvioletred" :red 213 :green 32 :blue 121}
     {:name "midnightblue" :red 47 :green 47 :blue 100}
     {:name "mintcream" :red 245 :green 255 :blue 250}
     {:name "mistyrose" :red 255 :green 228 :blue 225}
     {:name "mistyrose1" :red 255 :green 228 :blue 225}
     {:name "mistyrose2" :red 238 :green 213 :blue 210}
     {:name "mistyrose3" :red 205 :green 183 :blue 181}
     {:name "mistyrose4" :red 139 :green 125 :blue 123}
     {:name "moccasin" :red 255 :green 228 :blue 181}
     {:name "navajowhite" :red 255 :green 222 :blue 173}
     {:name "navajowhite1" :red 255 :green 222 :blue 173}
     {:name "navajowhite2" :red 238 :green 207 :blue 161}
     {:name "navajowhite3" :red 205 :green 179 :blue 139}
     {:name "navajowhite4" :red 139 :green 121 :blue 94}
     {:name "navy" :red 35 :green 35 :blue 117}
     {:name "navyblue" :red 35 :green 35 :blue 117}
     {:name "oldlace" :red 253 :green 245 :blue 230}
     {:name "olivedrab" :red 107 :green 142 :blue 35}
     {:name "olivedrab1" :red 192 :green 255 :blue 62}
     {:name "olivedrab2" :red 179 :green 238 :blue 58}
     {:name "olivedrab3" :red 154 :green 205 :blue 50}
     {:name "olivedrab4" :red 105 :green 139 :blue 34}
     {:name "orange" :red 255 :green 135 :blue 0}
     {:name "orange1" :red 255 :green 165 :blue 0}
     {:name "orange2" :red 238 :green 154 :blue 0}
     {:name "orange3" :red 205 :green 133 :blue 0}
     {:name "orange4" :red 139 :green 90 :blue 0}
     {:name "orangered" :red 255 :green 69 :blue 0}
     {:name "orangered1" :red 255 :green 69 :blue 0}
     {:name "orangered2" :red 238 :green 64 :blue 0}
     {:name "orangered3" :red 205 :green 55 :blue 0}
     {:name "orangered4" :red 139 :green 37 :blue 0}
     {:name "orchid" :red 239 :green 132 :blue 239}
     {:name "orchid1" :red 255 :green 131 :blue 250}
     {:name "orchid2" :red 238 :green 122 :blue 233}
     {:name "orchid3" :red 205 :green 105 :blue 201}
     {:name "orchid4" :red 139 :green 71 :blue 137}
     {:name "palegoldenrod" :red 238 :green 232 :blue 170}
     {:name "palegreen" :red 115 :green 222 :blue 120}
     {:name "palegreen1" :red 154 :green 255 :blue 154}
     {:name "palegreen2" :red 144 :green 238 :blue 144}
     {:name "palegreen3" :red 124 :green 205 :blue 124}
     {:name "palegreen4" :red 84 :green 139 :blue 84}
     {:name "paleturquoise" :red 175 :green 238 :blue 238}
     {:name "paleturquoise1" :red 187 :green 255 :blue 255}
     {:name "paleturquoise2" :red 174 :green 238 :blue 238}
     {:name "paleturquoise3" :red 150 :green 205 :blue 205}
     {:name "paleturquoise4" :red 102 :green 139 :blue 139}
     {:name "palevioletred" :red 219 :green 112 :blue 147}
     {:name "palevioletred1" :red 255 :green 130 :blue 171}
     {:name "palevioletred2" :red 238 :green 121 :blue 159}
     {:name "palevioletred3" :red 205 :green 104 :blue 137}
     {:name "palevioletred4" :red 139 :green 71 :blue 93}
     {:name "papayawhip" :red 255 :green 239 :blue 213}
     {:name "peachpuff" :red 255 :green 218 :blue 185}
     {:name "peachpuff1" :red 255 :green 218 :blue 185}
     {:name "peachpuff2" :red 238 :green 203 :blue 173}
     {:name "peachpuff3" :red 205 :green 175 :blue 149}
     {:name "peachpuff4" :red 139 :green 119 :blue 101}
     {:name "peru" :red 205 :green 133 :blue 63}
     {:name "pink" :red 255 :green 181 :blue 197}
     {:name "pink1" :red 255 :green 181 :blue 197}
     {:name "pink2" :red 238 :green 169 :blue 184}
     {:name "pink3" :red 205 :green 145 :blue 158}
     {:name "pink4" :red 139 :green 99 :blue 108}
     {:name "plum" :red 197 :green 72 :blue 155}
     {:name "plum1" :red 255 :green 187 :blue 255}
     {:name "plum2" :red 238 :green 174 :blue 238}
     {:name "plum3" :red 205 :green 150 :blue 205}
     {:name "plum4" :red 139 :green 102 :blue 139}
     {:name "powderblue" :red 176 :green 224 :blue 230}
     {:name "purple" :red 160 :green 32 :blue 240}
     {:name "purple1" :red 155 :green 48 :blue 255}
     {:name "purple2" :red 145 :green 44 :blue 238}
     {:name "purple3" :red 125 :green 38 :blue 205}
     {:name "purple4" :red 85 :green 26 :blue 139}
     {:name "red" :red 255 :green 0 :blue 0}
     {:name "red1" :red 245 :green 0 :blue 0}
     {:name "red2" :red 238 :green 0 :blue 0}
     {:name "red3" :red 205 :green 0 :blue 0}
     {:name "red4" :red 139 :green 0 :blue 0}
     {:name "rosybrown" :red 188 :green 143 :blue 143}
     {:name "rosybrown1" :red 255 :green 193 :blue 193}
     {:name "rosybrown2" :red 238 :green 180 :blue 180}
     {:name "rosybrown3" :red 205 :green 155 :blue 155}
     {:name "rosybrown4" :red 139 :green 105 :blue 105}
     {:name "royalblue" :red 65 :green 105 :blue 225}
     {:name "royalblue1" :red 72 :green 118 :blue 255}
     {:name "royalblue2" :red 67 :green 110 :blue 238}
     {:name "royalblue3" :red 58 :green 95 :blue 205}
     {:name "royalblue4" :red 39 :green 64 :blue 139}
     {:name "saddlebrown" :red 139 :green 69 :blue 19}
     {:name "salmon" :red 233 :green 150 :blue 122}
     {:name "salmon1" :red 255 :green 140 :blue 105}
     {:name "salmon2" :red 238 :green 130 :blue 98}
     {:name "salmon3" :red 205 :green 112 :blue 84}
     {:name "salmon4" :red 139 :green 76 :blue 57}
     {:name "sandybrown" :red 244 :green 164 :blue 96}
     {:name "seagreen" :red 82 :green 149 :blue 132}
     {:name "seagreen1" :red 84 :green 255 :blue 159}
     {:name "seagreen2" :red 78 :green 238 :blue 148}
     {:name "seagreen3" :red 67 :green 205 :blue 128}
     {:name "seagreen4" :red 46 :green 139 :blue 87}
     {:name "seashell" :red 255 :green 245 :blue 238}
     {:name "seashell1" :red 255 :green 245 :blue 238}
     {:name "seashell2" :red 238 :green 229 :blue 222}
     {:name "seashell3" :red 205 :green 197 :blue 191}
     {:name "seashell4" :red 139 :green 134 :blue 130}
     {:name "sienna" :red 150 :green 82 :blue 45}
     {:name "sienna1" :red 255 :green 130 :blue 71}
     {:name "sienna2" :red 238 :green 121 :blue 66}
     {:name "sienna3" :red 205 :green 104 :blue 57}
     {:name "sienna4" :red 139 :green 71 :blue 38}
     {:name "skyblue" :red 114 :green 159 :blue 255}
     {:name "skyblue1" :red 135 :green 206 :blue 255}
     {:name "skyblue2" :red 126 :green 192 :blue 238}
     {:name "skyblue3" :red 108 :green 166 :blue 205}
     {:name "skyblue4" :red 74 :green 112 :blue 139}
     {:name "slateblue" :red 126 :green 136 :blue 171}
     {:name "slateblue1" :red 131 :green 111 :blue 255}
     {:name "slateblue2" :red 122 :green 103 :blue 238}
     {:name "slateblue3" :red 105 :green 89 :blue 205}
     {:name "slateblue4" :red 71 :green 60 :blue 139}
     {:name "slategray" :red 112 :green 128 :blue 144}
     {:name "slategray1" :red 198 :green 226 :blue 255}
     {:name "slategray2" :red 185 :green 211 :blue 238}
     {:name "slategray3" :red 159 :green 182 :blue 205}
     {:name "slategray4" :red 108 :green 123 :blue 139}
     {:name "slategrey" :red 112 :green 128 :blue 144}
     {:name "snow" :red 255 :green 250 :blue 250}
     {:name "snow1" :red 255 :green 250 :blue 250}
     {:name "snow2" :red 238 :green 233 :blue 233}
     {:name "snow3" :red 205 :green 201 :blue 201}
     {:name "snow4" :red 139 :green 137 :blue 137}
     {:name "springgreen" :red 65 :green 172 :blue 65}
     {:name "springgreen1" :red 0 :green 255 :blue 127}
     {:name "springgreen2" :red 0 :green 238 :blue 118}
     {:name "springgreen3" :red 0 :green 205 :blue 102}
     {:name "springgreen4" :red 0 :green 139 :blue 69}
     {:name "steelblue" :red 84 :green 112 :blue 170}
     {:name "steelblue1" :red 99 :green 184 :blue 255}
     {:name "steelblue2" :red 92 :green 172 :blue 238}
     {:name "steelblue3" :red 79 :green 148 :blue 205}
     {:name "steelblue4" :red 54 :green 100 :blue 139}
     {:name "tan" :red 222 :green 184 :blue 135}
     {:name "tan1" :red 255 :green 165 :blue 79}
     {:name "tan2" :red 238 :green 154 :blue 73}
     {:name "tan3" :red 205 :green 133 :blue 63}
     {:name "tan4" :red 139 :green 90 :blue 43}
     {:name "thistle" :red 216 :green 191 :blue 216}
     {:name "thistle1" :red 255 :green 225 :blue 255}
     {:name "thistle2" :red 238 :green 210 :blue 238}
     {:name "thistle3" :red 205 :green 181 :blue 205}
     {:name "thistle4" :red 139 :green 123 :blue 139}
     {:name "tomato" :red 255 :green 99 :blue 71}
     {:name "tomato1" :red 255 :green 99 :blue 71}
     {:name "tomato2" :red 238 :green 92 :blue 66}
     {:name "tomato3" :red 205 :green 79 :blue 57}
     {:name "tomato4" :red 139 :green 54 :blue 38}
     {:name "transparent" :red 0 :green 0 :blue 1}
     {:name "turquoise" :red 25 :green 204 :blue 223}
     {:name "turquoise" :red 25 :green 204 :blue 223}
     {:name "turquoise1" :red 0 :green 245 :blue 255}
     {:name "turquoise2" :red 0 :green 229 :blue 238}
     {:name "turquoise3" :red 0 :green 197 :blue 205}
     {:name "turquoise4" :red 0 :green 134 :blue 139}
     {:name "violet" :red 156 :green 62 :blue 206}
     {:name "violetred" :red 243 :green 62 :blue 150}
     {:name "violetred1" :red 255 :green 62 :blue 150}
     {:name "violetred2" :red 238 :green 58 :blue 140}
     {:name "violetred3" :red 205 :green 50 :blue 120}
     {:name "violetred4" :red 139 :green 34 :blue 82}
     {:name "wheat" :red 245 :green 222 :blue 179}
     {:name "wheat1" :red 255 :green 231 :blue 186}
     {:name "wheat2" :red 238 :green 216 :blue 174}
     {:name "wheat3" :red 205 :green 186 :blue 150}
     {:name "wheat4" :red 139 :green 126 :blue 102}
     {:name "white" :red 255 :green 255 :blue 255}
     {:name "whitesmoke" :red 245 :green 245 :blue 245}
     {:name "yellow" :red 255 :green 255 :blue 0}
     {:name "yellow1" :red 255 :green 255 :blue 0}
     {:name "yellow2" :red 238 :green 238 :blue 0}
     {:name "yellow3" :red 205 :green 205 :blue 0}
     {:name "yellow4" :red 139 :green 139 :blue 0}
     {:name "yellowgreen" :red 50 :green 216 :blue 56}
])

(defn int->hex
  "Convert decimal to 2 character (leading zero) hex value. Assumes decimal between 0 and 255.
   Example:   (to-hex  5)
              ;;==>  05"
  [decnum]
  (format "%02x" decnum)
  )

(defn hex->int 
  "Convert a hex string to decimal. Assumes a 2-character hex value \"00\" thru \"FF\""
  [#^String s]
  (Integer/parseInt s 16))

(defn rgb->hexcolor 
  "Create string of 6 hex characters representing the RGB value.
   Example:   (rgb->hexcolor 255 255 255)
              ;;==> ffffff
"
  ([r g b]
   (str (int->hex r) (int->hex g) (int->hex b)))
  ([lst]
   (apply rgb->hexcolor lst))
   )

(defn hexcolor->rgb 
  "Converts a hex color code to RGB values.
   Example:    (hexcolor->rgb \"42ff34\")
               ;;==>  [66 255 52]
  "
  [hexcode]
  (conj (vector-of :int) (hex->int (subs hexcode 0 2)) (hex->int (subs hexcode 2 4)) (hex->int (subs hexcode 4 6))
        ))

(defn lcs 
  "Convenience method for coverting to lower case and removing internal spaces"
  [txt]
  (clojure.string/lower-case (clojure.string/replace txt #"\s" "")
))

(defn get-color-map
"Get the map corresponding to the correct color name. Default map is unix-color-map.
   Example:   (get-color-map \"White Smoke\")
              ;;==>   {:name \"whitesmoke\" :red 245 :green 245 :blue 245}

              (get-color-map \"Blue\" test-color-map)
              ;;==>   {:name \"blue\" :red 0 :green 0 :blue 255}
  
              (get-color-map \"White Smoke\" test-color-map)
              ;;==>   nil
"
  ([name]
   (first (filter #(= (:name %) (lcs name)) unix-color-map)))
  ([name mp]
   (first (filter #(= (:name %) (lcs name)) mp)))
)

(defn find-color 
  "Get the RGB values for the color name
   Example:   (find-color \"White Smoke\")
              ;;==>  {:red 245, :green 245, :blue 245}
"
  [color]
  (select-keys (get-color-map color) [:red :green :blue])
  )

(defn get-rgb
  "Extract RGB values as a list from the map of a color
   Example:   (get-rgb (find-color \"White Smoke\"))
              ;;==> (245 245 245)
"
  [mp]
  (vals mp)
)

(defn get-color-value 
  "Get the hex color value of the input RGB value"
  [rgb]
  (rgb->hexcolor (first rgb) (second rgb) (last rgb))
)

(defn get-rgb-color 
  "Get the RGB Color for a color name"
  [color]
  (get-rgb (find-color color))
)

(defn get-hex-color 
  "Get the HEX value for a color name.  Add a bit of cleanup to the result for non-existant color names."
  [color]
  (let  [clr (get-color-value (get-rgb-color color))]
  (if (= clr "nullnullnull")
    nil
   clr))
)

(defn get-color-names 
  "Provide a list of all the color names"
  []
  (map #(get % :name) unix-color-map)
)

(defn cmap
  "Provides a color mapping of the input color name
   Example:     (cmap \"coral\")
                ;;==>  {:name \"coral\", :color \"ff7256\"}
"
  [color]
  (dissoc (assoc (get-color-map color) :color (get-hex-color color)) :red :blue :green)
)

(defn remap-colors
  "Create a new map (default base unix-color-map) where each entry is {:name :color}
  For example, (remap-colors test-color-map)
               ;;==> [{:name \"blue\", :color \"0000ff\"
                      {:name \"green\", :color \"00ff00\"}
                      {:name \"red\", :color \"ff0000\"}]

   Note: Result is sorted by ascending color value/ 
"
  ([]
   (vec (sort-by :color (for [ix (range 0 (count unix-color-map))] (cmap (get (get unix-color-map ix) :name))))))
  ([mp]
   (vec (sort-by :color (for [ix (range 0 (count mp))] (cmap (get (get mp ix) :name)))) ))
)

(defn find-by-color 
  "Search for an entry which corresponds to a hex code.  If not found returns '() may return more than
   one color.
  Example:    (find-by-color \"454545\")
              ;;==>  ({:name \"gray27\", :color \"454545\"} {:name \"grey27\" :color \"454545\"})

              (find-by-color \"00ff00\" test-color-map)
              ;;==>   (:name \"green\", :color \"00ff00\"})
"
 ([hexcode]
  (filter #(= (:color %) hexcode) (remap-colors))
  ) 
  ([hexcode rmp]
  (filter #(= (:color %) hexcode) (remap-colors rmp))
   )
)

(defn names-by-color
  "Search for an entry which corresponds to a hex code.  If not found returns '() may return more than
   one color name.
   Example:    (names-by-color \"454545\")
               ;;==>  (\"gray27\" \"grey27\")
"
  ([hexcode]
  (map #(get % :name) (find-by-color hexcode)))
  ([hexcode rmp]
  (map #(get % :name) (find-by-color hexcode rmp)))
)

(defn add-hex
  "Adds 2 2-character hex values.  If greater than 255 (\"ff\") then limits to \"ff\""
  [ha hb]
  (let [na (hex->int ha) nb (hex->int hb)]
    (if (> (+ na nb) 255) "ff" (int->hex (+ na nb)))))

(defn sub-hex
  "Subtracts 2 2-character hex values.  If less than 0 (\"00\") then limits to \"00\""
  [ha hb]
  (let [na (hex->int ha) nb (hex->int hb)]
    (if (< (- na nb) 0) "00" (int->hex (- na nb))))
)

(defn add-rgb
  "Adds 2 color values.  If greater than 255 then limits to 255"
  [na nb]
    (if (> (+ na nb) 255) 255 (+ na nb)))

(defn sub-rgb
  "Subtracts 2 color values.  If less than 0 then limits to 0"
  [na nb]
  (if (> (- na nb) 0) 0 (- na nb)))

(defn- generate-adjustments 
  "Generates a map (of vectors) for + and - adjustments to RGB values."
  ([r g b]
   (let [s #{} lst ()] (into s (for [x (range (- r) (inc r)) y (range (- g) (inc g)) z (range (- b) (inc b)) ]
                                 [x y z]
                                 ))))
  ([lstrgb]
   (let [r (first lstrgb) g (second lstrgb) b (last lstrgb) s #{} lst ()]
     (into s (for [x (range (- r) (inc r)) y (range (- g) (inc g)) z (range (- b) (inc b)) ]
               [x y z]
               ))))
  )

(defn- get-closest-colors 
"Walks thru the adjustments data map and adds the rgb value to each triplet."
  [rgb data]
  (map rgb->hexcolor (into [] (clojure.walk/walk #(map add-rgb rgb %) identity data)))
)

(defn find-closest-color-list 
  "Provides a list of the closest colors to the input rgb value. Second argument is an input map like that 
   generated by (remap-colors).
   Example:     (find-closest-color-list '(166 42 40) (remap-colors))
                ;;==>   (\"brown\")
"
  ([rgb mp]
   (flatten (filter not-empty 
                    (map #(names-by-color % mp) (get-closest-colors rgb  (generate-adjustments 2 2 2))))))
  ([rgb mp depth]
   (flatten (filter not-empty 
                    (map #(names-by-color % mp) (get-closest-colors rgb  (generate-adjustments (repeat 3 depth)))))))
  )

(defn find-closest-color
  "Provides a list of the closest colors to the input hex coded color
   Example:    (find-closest-color \"808080\")
               ;;==> (\"gray\" \"grey\" \"gray51\" \"grey51\" \"gray50\" \"grey50\")
   If there is no color closest to value within a depth of 2, then '() is returned.
   If you get a () then you can try for a deeper search, for example,
   (find-closest-color \"808580\") will return (), so then try 
   (find-closest-color \"808580\" 3) which responds with (\"gray51\" \"grey51\").

   Keep in mind, the larger the depth, the more time is consumed.
   A depth of 2, generates a set of 125 elements and takes 0.43 msecs.
   A depth of 3. generates a set of 343 elements and takes 0.74 msecs.
   A depth of 10. generates a set of 9261 elements and takes 12.65 msecs.
"
  ([hexcode]
   (let [rmp (remap-colors) rgbval (hexcolor->rgb hexcode)]
     (if (not-empty (names-by-color hexcode rmp))
       (names-by-color hexcode rmp)
       (find-closest-color-list rgbval rmp)      
       ))
   )
  ([hexcode depth]
   (let [rmp (remap-colors) rgbval (hexcolor->rgb hexcode)]
     (if (not-empty (names-by-color hexcode rmp))
       (names-by-color hexcode rmp)
       (find-closest-color-list rgbval rmp depth)      
       )))
  )

(defn -main 
  "Simply prints som examples of various functions."
  []
  (println "Some sample uses:\n")
  (println "(get-hex-color \"SaddleBrown\")")
  (println "==> " (get-hex-color "SaddleBrown"))
  (println "\n(get-rgb-color \"light slate blue\")" )
  (println "==> " (get-rgb-color "light slate blue"))
  (println "\n(get-hex-color \"yuckky yellow\")") ()
  (println "==> nil")
  (println "")

)
