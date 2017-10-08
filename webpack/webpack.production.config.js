const config = require('./webpack.common.config.js');
const AotPlugin = require('@ngtools/webpack').AotPlugin;
const path = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');

// Using AOT main and vendor files.
config.entry.main = path.join(__dirname, '../src/frontend/scripts/main-aot.ts');
config.entry.vendor = path.join(__dirname, '../src/frontend/scripts/vendors/vendors-aot.ts');

// Using AOT TypeScript compiler.
config.module.rules.unshift(
    {
        test: /\.ts$/,
        use: '@ngtools/webpack',
        exclude: [/\.(spec|e2e)\.ts$/]
    }
);

config.plugins.push(
    // AOT Angular Plugin
    new AotPlugin({
        tsConfigPath: path.join(__dirname, '../tsconfig.json'),
        entryModule: path.join(__dirname, '../src/frontend/scripts/app.module#AppModule')
    }),
    // HTML Webpack Plugin with dev not present (aka false)
    new HtmlWebpackPlugin({
        template: path.join(__dirname, '../src/frontend/index.html.ejs'),
        favicon: path.join(__dirname, '../src/frontend/resources/favicons/favicon.ico'),
        filename: path.join(__dirname, '../target/classes/static/index.html'),
        inject: 'body',
        minify: {
            minifyCSS: true,
            minifyJS: true,
            removeComments: true,
            collapseWhitespace: true,
            collapseInlineTagWhitespace: true
        },
        chunksSortMode: 'dependency'
    })
);

config.devtool = 'source-map';
//config.output.sourceMapFilename = "";

module.exports = config;