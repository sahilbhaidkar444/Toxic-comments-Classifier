import pickle
from flask import Flask,request
import numpy
import re
import pyttsx3 as VA # Voice Assistant

vect = pickle.load(open("Model Files/vector.sav","rb"))
model = pickle.load(open("Model Files/classifier.sav","rb"))

def clean_text(text):
    text = text.lower()
    text = re.sub('\d+.','',text)
    text = ' '.join(text)
    text = ' '.join(re.findall('\w+',text))
    return text

app = Flask(__name__)

@app.route("/")
def home():
    return "こんにちは、みんな (Hello Everyone)"

@app.route("/prediction",methods = ['POST'])
def result():
    text = request.form.get('text')
    text = clean_text(text)
    v = vect.transform([text])
    prediction = model.predict(v)
    final = prediction[0]
    return {'prediction':int(final)}


if __name__=='__main__':
    app.run(debug=True)



